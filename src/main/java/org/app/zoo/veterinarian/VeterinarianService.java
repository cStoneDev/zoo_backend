package org.app.zoo.veterinarian;


import org.app.zoo.veterinarian.dto.out.VeterinarianOutputDTO;



import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;


    public VeterinarianService(VeterinarianRepository veterinarianRepository) {
        this.veterinarianRepository = veterinarianRepository;
    }

    public VeterinarianOutputDTO findById(Integer id) {
        // Obtener el veterinario por ID
        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con ID: " + id));

        
        // Retornar el DTO de salida
        return mapToOutputDTO(veterinarian);
    }

    // Método para mapear la entidad Veterinarian a su DTO de salida
    private VeterinarianOutputDTO mapToOutputDTO(Veterinarian veterinarian) {
        return new VeterinarianOutputDTO(
                veterinarian.getFax(),
                veterinarian.getCityDistance(),
                veterinarian.getClinic().getId(),
                veterinarian.getSpeciality().getId(),
                veterinarian.getClinic().getName(),
                veterinarian.getSpeciality().getName()
        );
    }
}

