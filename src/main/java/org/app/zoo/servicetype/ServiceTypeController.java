package org.app.zoo.servicetype;

import org.app.zoo.servicetype.dto.in.ServiceTypeInputDTO;
import org.app.zoo.servicetype.dto.out.ServiceTypeOutputDTO;

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
@RequestMapping("/servicetype")
@Schema(description = "ServiceType controller class to handle HTTP requests")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService){
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping
    public ResponseEntity<ServiceTypeOutputDTO> createServiceType(@RequestBody ServiceTypeInputDTO ServiceTypeInputDTO){
        return new ResponseEntity<>(serviceTypeService.createServiceType(ServiceTypeInputDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public Page<ServiceTypeOutputDTO> getAllServiceTypes(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return serviceTypeService.getAllServiceTypes(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable int id) {
        serviceTypeService.deleteServiceType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTypeOutputDTO> findServiceType(@PathVariable int id) {
        ServiceTypeOutputDTO serviceType = serviceTypeService.findById(id);
        return ResponseEntity.ok(serviceType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTypeOutputDTO> updateServiceType(@PathVariable int id, @RequestBody ServiceTypeInputDTO updatedServiceType) {
        ServiceTypeOutputDTO serviceType = serviceTypeService.updateServiceType(id, updatedServiceType);
        return ResponseEntity.ok(serviceType);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ServiceTypeOutputDTO>> searchServiceType(@RequestBody ServiceTypeSearchCriteria serviceTypeSearchCriteria) {
        
        return new ResponseEntity<>(serviceTypeService.searchServiceType(serviceTypeSearchCriteria) , HttpStatus.OK);
        
    }
}
