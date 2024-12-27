package org.app.zoo.clinic;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class ClinicSpecification {
    public static Specification<Clinic> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchField.toLowerCase() + "%"));
                return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }
}
