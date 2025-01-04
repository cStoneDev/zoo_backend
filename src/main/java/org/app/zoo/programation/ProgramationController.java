package org.app.zoo.programation;

import org.app.zoo.programation.dto.in.ProgramationInputDTO;
import org.app.zoo.programation.dto.out.ProgramationOutputDTO;
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
@RequestMapping("/programation")
@Schema(description = "Programation controller class to handle HTTP requests")
public class ProgramationController {
    
    private final ProgramationService programationService;

    public ProgramationController(ProgramationService programationService) {
        this.programationService = programationService;
    }

    @PostMapping
    public ResponseEntity<ProgramationOutputDTO> createProgramation(@RequestBody ProgramationInputDTO programation) {
        return new ResponseEntity<>(programationService.createProgramation(programation), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ProgramationOutputDTO> getAllProgramation(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return programationService.getAllProgramation(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramation(@PathVariable int id) {
        programationService.deleteProgramation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramationOutputDTO> findProgramation(@PathVariable int id) {
        ProgramationOutputDTO programation = programationService.findById(id);
        return ResponseEntity.ok(programation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramationOutputDTO> updateProgramation(@PathVariable int id, @RequestBody ProgramationInputDTO updatedProgramation) {
        ProgramationOutputDTO programation = programationService.updateProgramation(id, updatedProgramation);
        return ResponseEntity.ok(programation);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ProgramationOutputDTO>> searchProgramation(@RequestBody ProgramationSearchCriteria programationSearchCriteria) {
        
        return new ResponseEntity<>(programationService.searchProgramation(programationSearchCriteria) , HttpStatus.OK);
        
    }
}
