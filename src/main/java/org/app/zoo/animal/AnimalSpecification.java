package org.app.zoo.animal;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class AnimalSpecification {

    public static Specification<Animal> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de raza
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("breed").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de especie
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("breed").get("species").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Intentar agregar un filtro por edad
            try {
                int ageValue = Integer.parseInt(searchField);
                predicates.add(criteriaBuilder.equal(root.get("age"), ageValue));
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }

            // Intentar agregar un filtro por peso
            try {
                double weightValue = Double.parseDouble(searchField);
                predicates.add(criteriaBuilder.equal(root.get("weight"), weightValue));
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }

            // Intentar agregar un filtro por fecha de ingreso
            try {
                java.sql.Date entryDateValue = java.sql.Date.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("entryDate"), entryDateValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            // Combinar todos los predicados usando OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }

    public static Specification<Animal> filterByBreed(int breedId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID de la raza
            return criteriaBuilder.equal(root.get("breed").get("id"), breedId);
        };
    }

    public static Specification<Animal> filterBySpecies(int speciesId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID de la raza
            return criteriaBuilder.equal(root.get("breed").get("species").get("id"), speciesId);
        };
    }
    
    public static Specification<Animal> filterByAge(int min, int max) {
        return (root, query, criteriaBuilder) -> {
            
            return criteriaBuilder.between(root.get("age"), min, max);
        };
    }
    public static Specification<Animal> filterByWeight(double min, double max) {
        return (root, query, criteriaBuilder) -> {
            
            return criteriaBuilder.between(root.get("weight"), min, max);
        };
    }

    public static Specification<Animal> filterByDaysInShelter(int min, int max) {
        return (root, query, criteriaBuilder) -> {
            // Obtener la fecha actual
            LocalDate today = LocalDate.now();

            // Calcular las fechas límite
            LocalDate minDate = today.minusDays(max); // Fecha máxima (menos días)
            LocalDate maxDate = today.minusDays(min); // Fecha mínima (más días)

            // Convertir LocalDate a Date
            Date minDateConverted = Date.from(minDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date maxDateConverted = Date.from(maxDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Filtrar por el rango de fechas
            return criteriaBuilder.between(root.get("entryDate"), minDateConverted, maxDateConverted);
        };
    }

}
