package org.app.zoo.providertype;

import java.util.List;

import org.app.zoo.providertype.dto.in.ProviderTypeInputDTO;
import org.app.zoo.providertype.dto.out.ProviderTypeOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<ProviderTypeOutputDTO> getAllProviderTypes(){
        return providerTypeService.getAllProviderTypes();
    }

}
