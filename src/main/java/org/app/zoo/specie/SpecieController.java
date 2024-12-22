package org.app.zoo.specie;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/species")
@Schema(description = "Specie controller class to handle HTTP requests")
public class SpecieController {

    private final SpecieService specieService;

    public SpecieController(SpecieService specieService) {
        this.specieService = specieService;
    }

    @PostMapping
    public ResponseEntity<Specie> createSpecie(@RequestBody Specie specie) {
        return new ResponseEntity<>(specieService.createSpecie(specie), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Specie> getAllSpecies() {
        return specieService.getAllSpecies();
    }
}
