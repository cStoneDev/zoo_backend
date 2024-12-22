package org.app.zoo.specie;

import java.util.List;

import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Specie service who has the implementations of crud functions and more")
public class SpecieService {

    private final SpecieRepository specieRepository;

    public SpecieService(SpecieRepository specieRepository) {
        this.specieRepository = specieRepository;
    }

    public Specie createSpecie(Specie specie) {
        return specieRepository.save(specie);
    }

    public List<Specie> getAllSpecies() {
        return specieRepository.findAll();
    }
}
