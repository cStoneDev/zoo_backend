package org.app.zoo.contract;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class ContractSpecification {
    public static Specification<Contract> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre de proveedor
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("provider").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de tipo de proveedor
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("provider").get("providerType").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Intentar agregar un filtro por fecha de inicio
            try {
                java.sql.Date startingDateValue = java.sql.Date.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("startingDate"), startingDateValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            // Intentar agregar un filtro por fecha de terminacion
            try {
                java.sql.Date endingDateValue = java.sql.Date.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("endingDate"), endingDateValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            // Intentar agregar un filtro por fecha de conciliacion
            try {
                java.sql.Date conciliationDateValue = java.sql.Date.valueOf(searchField);
                predicates.add(criteriaBuilder.equal(root.get("conciliationDate"), conciliationDateValue));
            } catch (IllegalArgumentException e) {
                // Ignorar si no es una fecha válida
            }

            // Intentar agregar un filtro por peso
            try {
                double basePriceValue = Double.parseDouble(searchField);
                predicates.add(criteriaBuilder.equal(root.get("basePrice"), basePriceValue));
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }

            // Combinar todos los predicados usando OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }

    // contratos activos
    public static Specification<Contract> filterByActiveContracts() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("endingDate"), LocalDate.now()),
                criteriaBuilder.lessThanOrEqualTo(root.get("startingDate"), LocalDate.now())
            );
    }

    // contratos no activos
    public static Specification<Contract> filterByNoActiveContracts() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.lessThan(root.get("endingDate"), LocalDate.now());
    }

    // contratos futuros
    public static Specification<Contract> filterByFutureContracts() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThan(root.get("startingDate"), LocalDate.now());
    }

    public static Specification<Contract> filterByProviderTypeId(int providerTypeId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID del tipo de proveedor
            return criteriaBuilder.equal(root.get("provider").get("providerType").get("id"), providerTypeId);
        };
    }

    public static Specification<Contract> filterByBasePrice(double min, double max) {
        return (root, query, criteriaBuilder) -> {
            
            return criteriaBuilder.between(root.get("basePrice"), min, max);
        };
    }
}
