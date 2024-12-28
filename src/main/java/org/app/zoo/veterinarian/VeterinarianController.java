package org.app.zoo.veterinarian;


/////////// Not completelly sure if this would be needed





/*import org.app.zoo.provider.Provider;
import org.app.zoo.veterinarian.dto.in.VeterinarianInputDTO;
import org.app.zoo.veterinarian.dto.out.VeterinarianOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/veterinarians")
@Schema(description = "Veterinarian controller class to handle HTTP requests")
public class VeterinarianController {

    private final VeterinarianService veterinarianService;

    public VeterinarianController(VeterinarianService veterinarianService) {
        this.veterinarianService = veterinarianService;
    }

    @PostMapping
    public ResponseEntity<VeterinarianOutputDTO> createVeterinarian(@RequestBody VeterinarianInputDTO veterinarianInputDTO) {
        // La creación de un veterinario se asume como parte de la creación de un proveedor
        return new ResponseEntity<>(veterinarianService.createVeterinarian(veterinarianInputDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<VeterinarianOutputDTO> getAllVeterinarians(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return veterinarianService.getAllVeterinarians(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinarian(@PathVariable int id) {
        veterinarianService.deleteVeterinarian(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianOutputDTO> findVeterinarian(@PathVariable Integer id) {
        VeterinarianOutputDTO veterinarian = veterinarianService.findById(id);
        return ResponseEntity.ok(veterinarian);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<VeterinarianOutputDTO> updateVeterinarian(
        @PathVariable int id,
        @RequestBody VeterinarianInputDTO updatedVeterinarian
    ) {
        VeterinarianOutputDTO veterinarian = veterinarianService.updateVeterinarian(id, updatedVeterinarian);
        return ResponseEntity.ok(veterinarian);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<VeterinarianOutputDTO>> searchVeterinarians(
        @RequestBody VeterinarianSearchCriteria veterinarianSearchCriteria
    ) {
        return new ResponseEntity<>(veterinarianService.searchVeterinarians(veterinarianSearchCriteria), HttpStatus.OK);
    }
} */
