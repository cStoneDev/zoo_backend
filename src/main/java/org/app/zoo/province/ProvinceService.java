package org.app.zoo.province;

import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.province.dto.in.ProvinceInputDTO;
import org.app.zoo.province.dto.out.ProvinceOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Province service who has the implementations of crud functions and more")
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public ProvinceOutputDTO createProvince(ProvinceInputDTO province) {
        if (province.name() == null || province.name().isEmpty()){
            throw new InvalidInputException("El nombre de la provincia no puede estar vacío");
        }
        Province provinceSave = new Province(province.name());
        provinceRepository.save(provinceSave);
        return mapToOutputDTO(provinceSave);
    }

    public void deleteProvince(int id) {
        // Verificar si la provincia existe
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provincia no encontrada"));

        try {
            provinceRepository.delete(province);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar la provincia porque tiene dependencias relacionadas.");
        }
    }

    public ProvinceOutputDTO findById(int id) {
        return mapToOutputDTO(provinceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Provincia no encontrada")));
    }

    public ProvinceOutputDTO updateProvince(int id, ProvinceInputDTO updatedProvince) {
        // Verificar si la provincia existe
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provincia no encontrada"));

        // Validar que el nombre no esté vacío
        if (updatedProvince.name() == null || updatedProvince.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la provincia no puede estar vacío");
        }

        // Validar que no exista otra provincia con el mismo nombre
        boolean exists = provinceRepository.existsByNameAndIdNot(updatedProvince.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una provincia con el nombre: " + updatedProvince.name());
        }

        // Actualizar los valores de la provincia
        province.setName(updatedProvince.name());
        // (Actualizar otros campos según corresponda)

        // Guardar y devolver la provincia actualizada
        provinceRepository.save(province);
        return mapToOutputDTO(province);
    }


    public Page<ProvinceOutputDTO> getAllProvince(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de speciees según la paginación
        Page<Province> provincePage = provinceRepository.findAll(pageable);
    
        // Convertir a DTOs
        return provincePage.map(this::mapToOutputDTO);
    }

    private ProvinceOutputDTO mapToOutputDTO(Province province) {
        ProvinceOutputDTO provinceOutputDTO = new ProvinceOutputDTO(
            province.getId(),
            province.getName()
        );
        return provinceOutputDTO;
    }


    public Page<ProvinceOutputDTO> searchProvince(ProvinceSearchCriteria criteria) {
        // Todo esta validado en provinceSpecification

        Specification<Province> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(ProvinceSpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de speciees según la especificación y la paginación
        Page<Province> provincePage = provinceRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return provincePage.map(this::mapToOutputDTO);
    }
}
