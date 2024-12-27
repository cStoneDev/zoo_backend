package org.app.zoo.clinic;

import org.app.zoo.clinic.dto.in.ClinicInputDTO;
import org.app.zoo.clinic.dto.out.ClinicOutputDTO;
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
@RequestMapping("/clinic")
@Schema(description = "Clinic controller class to handle HTTP requests")
public class ClinicController {
    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping
    public ResponseEntity<ClinicOutputDTO> createClinic(@RequestBody ClinicInputDTO clinic) {
        return new ResponseEntity<>(clinicService.createClinic(clinic), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ClinicOutputDTO> getAllClinic(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return clinicService.getAllClinic(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable int id) {
        clinicService.deleteClinic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicOutputDTO> findClinic(@PathVariable int id) {
        ClinicOutputDTO clinic = clinicService.findById(id);
        return ResponseEntity.ok(clinic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicOutputDTO> updateClinic(@PathVariable int id, @RequestBody ClinicInputDTO updatedClinic) {
        ClinicOutputDTO clinic = clinicService.updateClinic(id, updatedClinic);
        return ResponseEntity.ok(clinic);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ClinicOutputDTO>> searchClinic(@RequestBody ClinicSearchCriteria clinicSearchCriteria) {
        
        return new ResponseEntity<>(clinicService.searchClinic(clinicSearchCriteria) , HttpStatus.OK);
        
    }

}
