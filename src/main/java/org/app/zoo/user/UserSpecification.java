package org.app.zoo.user;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
    public static Specification<User> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + searchField.toLowerCase() + "%"));
            
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("role").get("name")), "%" + searchField.toLowerCase() + "%"));
            
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchField.toLowerCase() + "%"));


            return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }

    public static Specification<User> filterByRole(int roleId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID de la raza
            return criteriaBuilder.equal(root.get("role").get("id"), roleId);
        };
    }
}
