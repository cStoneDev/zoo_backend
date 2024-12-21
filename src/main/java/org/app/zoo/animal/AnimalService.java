package org.app.zoo.animal;

import java.util.List;

import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Animal service who has the implementations of crud functions and more")
public class AnimalService {
    
    private AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAllAnimals(){
        return animalRepository.findAll();
    }
}
