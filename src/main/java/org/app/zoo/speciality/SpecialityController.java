package org.app.zoo.speciality;

import org.app.zoo.speciality.dto.in.SpecialityInputDTO;
import org.app.zoo.speciality.dto.out.SpecialityOutputDTO;
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
@RequestMapping("/speciality")
@Schema(description = "Speciality controller class to handle HTTP requests")
public class SpecialityController {
    
    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @PostMapping
    public ResponseEntity<SpecialityOutputDTO> createSpeciality(@RequestBody SpecialityInputDTO speciality) {
        return new ResponseEntity<>(specialityService.createSpeciality(speciality), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<SpecialityOutputDTO> getAllSpeciality(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return specialityService.getAllSpeciality(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable int id) {
        specialityService.deleteSpeciality(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialityOutputDTO> findSpeciality(@PathVariable int id) {
        SpecialityOutputDTO speciality = specialityService.findById(id);
        return ResponseEntity.ok(speciality);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialityOutputDTO> updateSpeciality(@PathVariable int id, @RequestBody SpecialityInputDTO updatedSpeciality) {
        SpecialityOutputDTO speciality = specialityService.updateSpeciality(id, updatedSpeciality);
        return ResponseEntity.ok(speciality);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<SpecialityOutputDTO>> searchSpeciality(@RequestBody SpecialitySearchCriteria specialitySearchCriteria) {
        
        return new ResponseEntity<>(specialityService.searchSpeciality(specialitySearchCriteria) , HttpStatus.OK);
        
    }
}
