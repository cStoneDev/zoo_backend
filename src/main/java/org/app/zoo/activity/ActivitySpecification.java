package org.app.zoo.activity;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class ActivitySpecification {
    public static Specification<Activity> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre de proveedor
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contract").get("provider").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de tipo de proveedor
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contract").get("provider").get("providerType").get("name")), "%" + searchField.toLowerCase() + "%"));

            try {
                java.sql.Date dateValue = java.sql.Date.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("date"), dateValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            try {
                java.sql.Time timeValue = java.sql.Time.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("time"), timeValue));
            } catch (IllegalArgumentException e) {
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }

    public static Specification<Activity> filterByActiveActivities(){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.and( 
                criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.now())
            );
    }

    public static Specification<Activity> filterByNoActiveActivities(){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThan(root.get("date"), LocalDate.now());
    }

    public static Specification<Activity> filterByProviderTypeId(int providerTypeId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("contract").get("provider").get("providerType").get("id"), providerTypeId);
        };
    }

    public static Specification<Activity> filterByContractId(int contractId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("contract").get("id"), contractId);
        };
    }
}
