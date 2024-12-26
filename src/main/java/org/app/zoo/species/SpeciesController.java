package org.app.zoo.species;


import org.app.zoo.species.dto.in.SpeciesInputDTO;
import org.app.zoo.species.dto.out.SpeciesOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/species")
@Schema(description = "Species controller class to handle HTTP requests")
public class SpeciesController {

    private final SpeciesService speciesService;

    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @PostMapping
    public ResponseEntity<SpeciesOutputDTO> createSpecies(@RequestBody SpeciesInputDTO species) {
        return new ResponseEntity<>(speciesService.createSpecies(species), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<SpeciesOutputDTO> getAllSpecies(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return speciesService.getAllSpecies(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable int id) {
        speciesService.deleteSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesOutputDTO> findSpecies(@PathVariable int id) {
        SpeciesOutputDTO species = speciesService.findById(id);
        return ResponseEntity.ok(species);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeciesOutputDTO> updateSpecies(@PathVariable int id, @RequestBody SpeciesInputDTO updatedSpecies) {
        SpeciesOutputDTO species = speciesService.updateSpecies(id, updatedSpecies);
        return ResponseEntity.ok(species);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<SpeciesOutputDTO>> searchSpecies(@RequestBody SpeciesSearchCriteria speciesSearchCriteria) {
        
        return new ResponseEntity<>(speciesService.searchSpecies(speciesSearchCriteria) , HttpStatus.OK);
        
    }

}
