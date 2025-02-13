package org.app.zoo.animal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

import org.app.zoo.animal.dto.in.AnimalInputDTO;
import org.app.zoo.animal.dto.out.AnimalOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/animals")
@Schema(description = "Animal controller class to handle HTTP requests")
public class AnimalController {

    private AnimalService animalService;

    public AnimalController(AnimalService animalService){
        this.animalService = animalService;
    }

    @PostMapping
    public ResponseEntity<AnimalOutputDTO> createAnimal(@RequestBody AnimalInputDTO animalInputDTO) {
        return new ResponseEntity<>(animalService.createAnimal(animalInputDTO), HttpStatus.CREATED);
    }

    @GetMapping //it's called when /animals receives a GET request
    public Page<AnimalOutputDTO> getAllAnimals(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return animalService.getAllAnimals(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable int id) {
        animalService.deleteAnimal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalOutputDTO> findAnimal(@PathVariable int id) {
        AnimalOutputDTO animal = animalService.findById(id);
        return ResponseEntity.ok(animal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalOutputDTO> updateAnimal(@PathVariable int id, @RequestBody AnimalInputDTO updatedAnimal) {
        AnimalOutputDTO animal = animalService.updateAnimal(id, updatedAnimal);
        return ResponseEntity.ok(animal);
    }
    
    @PostMapping("/search")
    public ResponseEntity<Page<AnimalOutputDTO>> searchAnimals(@RequestBody AnimalSearchCriteria animalSearchCriteria) {
        
        return new ResponseEntity<>(animalService.searchAnimals(animalSearchCriteria) , HttpStatus.OK);
        
    }
    
}
