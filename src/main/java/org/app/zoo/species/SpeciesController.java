package org.app.zoo.species;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Species> createSpecie(@RequestBody Species species) {
        return new ResponseEntity<>(speciesService.createSpecies(species), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Species> getAllSpecies() {
        return speciesService.getAllSpecies();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecie(@PathVariable int id) {
        speciesService.deleteSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }
}
