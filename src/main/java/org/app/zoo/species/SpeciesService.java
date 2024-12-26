package org.app.zoo.species;

import java.util.List;

import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Species service who has the implementations of crud functions and more")
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Species createSpecies(Species species) {
        if (species.getName() == null || species.getName().isEmpty()){
            throw new InvalidInputException("El nombre de la especie no puede estar vacío");
        }
        return speciesRepository.save(species);
    }

    public List<Species> getAllSpecies() {
        return speciesRepository.findAll();
    }

    public void deleteSpecies(int id) {
        // Verificar si la especie existe
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especie no encontrada"));

        try {
            speciesRepository.delete(species);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar la especie porque tiene dependencias relacionadas.");
        }
    }
}
