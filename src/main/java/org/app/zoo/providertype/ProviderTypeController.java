package org.app.zoo.providertype;

import org.app.zoo.providertype.dto.in.ProviderTypeInputDTO;
import org.app.zoo.providertype.dto.out.ProviderTypeOutputDTO;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/providertype")
@Schema(description = "ProviderType controller class to handle HTTP requests")
public class ProviderTypeController {
    private final ProviderTypeService providerTypeService;

    public ProviderTypeController(ProviderTypeService providerTypeService){
        this.providerTypeService = providerTypeService;
    }

    @PostMapping
    public ResponseEntity<ProviderTypeOutputDTO> createProviderType(@RequestBody ProviderTypeInputDTO providerTypeInputDTO){
        return new ResponseEntity<>(providerTypeService.createProviderType(providerTypeInputDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public Page<ProviderTypeOutputDTO> getAllProviderTypes(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return providerTypeService.getAllProviderTypes(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProviderType(@PathVariable int id) {
        providerTypeService.deleteProviderType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderTypeOutputDTO> findProviderType(@PathVariable int id) {
        ProviderTypeOutputDTO providerType = providerTypeService.findById(id);
        return ResponseEntity.ok(providerType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderTypeOutputDTO> updateProviderType(@PathVariable int id, @RequestBody ProviderTypeInputDTO updatedProviderType) {
        ProviderTypeOutputDTO providerType = providerTypeService.updateProviderType(id, updatedProviderType);
        return ResponseEntity.ok(providerType);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ProviderTypeOutputDTO>> searchProviderType(@RequestBody ProviderTypeSearchCriteria providerTypeSearchCriteria) {
        
        return new ResponseEntity<>(providerTypeService.searchProviderType(providerTypeSearchCriteria) , HttpStatus.OK);
        
    }
}
