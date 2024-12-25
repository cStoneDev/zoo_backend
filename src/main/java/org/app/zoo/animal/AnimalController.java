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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @PostMapping("/search")
    public ResponseEntity<Page<AnimalOutputDTO>> searchAnimals(@RequestBody AnimalSearchCriteria animalSearchCriteria) {
        
        return new ResponseEntity<>(animalService.searchAnimals(animalSearchCriteria) , HttpStatus.OK);
        
    }
    
}
