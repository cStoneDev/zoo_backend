package org.app.zoo.provider;

import org.app.zoo.provider.dto.in.ProviderInputDTO;
import org.app.zoo.provider.dto.out.ProviderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    // Crear un nuevo proveedor (opcionalmente con veterinario)
    @PostMapping
    public ResponseEntity<ProviderResponseDTO> createProvider(@RequestBody ProviderInputDTO providerInputDTO) {
        ProviderResponseDTO provider = providerService.createProvider(providerInputDTO);
        return ResponseEntity.ok(provider);
    }

    // Obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> getProviderById(@PathVariable int id) {
        ProviderResponseDTO provider = providerService.findById(id);
        return ResponseEntity.ok(provider);
    }

    // Listar todos los proveedores
    @GetMapping
    public ResponseEntity<Page<ProviderResponseDTO>> getAllProviders(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        
        return new ResponseEntity<>(providerService.getAllProviders(pageNumber,pageSize),HttpStatus.OK);
    }

    // Actualizar un proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> updateProvider(
            @PathVariable int id,
            @RequestBody ProviderInputDTO providerInputDTO) {
        ProviderResponseDTO updatedProvider = providerService.updateProvider(id, providerInputDTO);
        return ResponseEntity.ok(updatedProvider);
    }

    // Eliminar un proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable int id) {
        providerService.deleteProvider(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
