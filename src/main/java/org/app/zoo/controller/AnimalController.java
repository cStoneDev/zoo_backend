package org.app.zoo.controller;

import java.util.List;

import org.app.zoo.model.Animal;
import org.app.zoo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/animals")
@Schema(description = "Animal controller class to handle HTTP requests")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping //it's called when /animals receives a GET request
    public List<Animal> getAllAnimals(){
        return animalService.getAllAnimals();
    }
    
    
}
