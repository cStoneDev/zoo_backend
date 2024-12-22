package org.app.zoo.breed;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/breeds")
@Schema(description = "Breed controller class to handle HTTP requests")
public class BreedController {
    
    private final BreedService breedService;

    
    public BreedController(BreedService breedService) {
        this.breedService = breedService;
    }

    @PostMapping
    public ResponseEntity<Breed> createBreed(@RequestBody Breed breed) {
        return new ResponseEntity<>(breedService.createBreed(breed), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Breed> getAllBreeds() {
        return breedService.getAllBreeds();
    }

}
