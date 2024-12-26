package org.app.zoo.breed;

import org.app.zoo.breed.dto.in.BreedInputDTO;
import org.app.zoo.breed.dto.out.BreedOutputDTO;
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
@RequestMapping("/breeds")
@Schema(description = "Breed controller class to handle HTTP requests")
public class BreedController {
    
    private final BreedService breedService;

    
    public BreedController(BreedService breedService) {
        this.breedService = breedService;
    }

    @PostMapping
    public ResponseEntity<BreedOutputDTO> createBreed(@RequestBody BreedInputDTO breed) {
        return new ResponseEntity<>(breedService.createBreed(breed), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<BreedOutputDTO> getAllBreeds(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return breedService.getAllBreed(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreed(@PathVariable int id) {
        breedService.deleteBreed(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedOutputDTO> findBreed(@PathVariable int id) {
        BreedOutputDTO breed = breedService.findById(id);
        return ResponseEntity.ok(breed);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreedOutputDTO> updateBreed(@PathVariable int id, @RequestBody BreedInputDTO updatedBreed) {
        BreedOutputDTO breed = breedService.updateBreed(id, updatedBreed);
        return ResponseEntity.ok(breed);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<BreedOutputDTO>> searchBreed(@RequestBody BreedSearchCriteria breedSearchCriteria) {
        
        return new ResponseEntity<>(breedService.searchBreed(breedSearchCriteria) , HttpStatus.OK);
        
    }
}
