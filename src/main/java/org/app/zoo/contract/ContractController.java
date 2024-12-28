package org.app.zoo.contract;


import org.app.zoo.contract.dto.in.ContractInputDTO;
import org.app.zoo.contract.dto.out.ContractOutputDTO;
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
@RequestMapping("/contract")
@Schema(description = "Contract controller class to handle HTTP requests")
public class ContractController {
    
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractOutputDTO> createContract(@RequestBody ContractInputDTO contract) {
        return new ResponseEntity<>(contractService.createContract(contract), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ContractOutputDTO> getAllContract(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return contractService.getAllContract(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable int id) {
        contractService.deleteContract(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractOutputDTO> findContract(@PathVariable int id) {
        ContractOutputDTO contract = contractService.findById(id);
        return ResponseEntity.ok(contract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractOutputDTO> updateContract(@PathVariable int id, @RequestBody ContractInputDTO updatedContract) {
        ContractOutputDTO contract = contractService.updateContract(id, updatedContract);
        return ResponseEntity.ok(contract);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ContractOutputDTO>> searchContract(@RequestBody ContractSearchCriteria contractSearchCriteria) {
        
        return new ResponseEntity<>(contractService.searchContract(contractSearchCriteria) , HttpStatus.OK);
        
    }
}
