package org.app.zoo.programation;


import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class ProgramationSpecification{
    public static Specification<Programation> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre de animal
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("animal").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por raza de animal
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("animal").get("breed").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por especie de animal
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("animal").get("breed").get("species").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Intentar agregar un filtro por fecha de actividad
            try {
                java.sql.Date dateValue = java.sql.Date.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("activity").get("date"), dateValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            // Internar agregar un filtro por hora de actividad
            try {
                java.sql.Time timeValue = java.sql.Time.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("activity").get("time"), timeValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            // Combinar todos los predicados usando OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }

    public static Specification<Programation> filterByActivityDateRange(Date startDate, Date endDate) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por rango de fechas
            return criteriaBuilder.between(root.get("activity").get("date"), startDate, endDate);
        };
    }

    public static Specification<Programation> filterByActivityTimeRange(Time startTime, Time endTime) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por rango de fechas
            return criteriaBuilder.between(root.get("activity").get("time"), startTime, endTime);
        };
    }

    public static Specification<Programation> filterByAnimalSpecies(int speciesId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID de la raza
            return criteriaBuilder.equal(root.get("animal").get("breed").get("species").get("id"), speciesId);
        };
    }

}