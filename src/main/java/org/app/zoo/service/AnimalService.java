package org.app.zoo.service;

import java.util.List;

import org.app.zoo.model.Animal;
import org.app.zoo.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.ApiModel;

@Service
@ApiModel(description = "Animal service who has the implementations of crud functions and more")
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> getAllAnimals(){
        return animalRepository.findAll();
    }
}
