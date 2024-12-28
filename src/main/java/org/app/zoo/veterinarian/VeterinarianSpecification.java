package org.app.zoo.veterinarian;

/*import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class VeterinarianSpecification {

    public static Specification<Veterinarian> filterBySearchField(String searchField) {
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por apellido
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por clínica
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("clinic").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por fax
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fax")), "%" + searchField.toLowerCase() + "%"));

            // Intentar agregar un filtro por distancia
            try {
                double distanceValue = Double.parseDouble(searchField);
                predicates.add(criteriaBuilder.equal(root.get("cityDistance"), distanceValue));
            } catch (NumberFormatException e) {
                // Ignorar si no es un número válido
            }

            // Combinar todos los predicados usando OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Veterinarian> filterByClinic(int clinicId) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por ID de clínica
            return criteriaBuilder.equal(root.get("clinic").get("id"), clinicId);
        };
    }

    public static Specification<Veterinarian> filterByProvider(int providerId) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por ID de proveedor
            return criteriaBuilder.equal(root.get("provider").get("id"), providerId);
        };
    }

    public static Specification<Veterinarian> filterBySpeciality(int specialityId) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por ID de especialidad
            return criteriaBuilder.equal(root.get("speciality").get("id"), specialityId);
        };
    }

    public static Specification<Veterinarian> filterByDistance(double min, double max) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por rango de distancia
            return criteriaBuilder.between(root.get("cityDistance"), min, max);
        };
    }

    public static Specification<Veterinarian> filterByFax(String fax) {
        return (root, query, criteriaBuilder) -> {
            // Filtrar por número de fax exacto (case-insensitive)
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fax")), "%" + fax.toLowerCase() + "%");
        };
    }

    public static Specification<Veterinarian> buildFromCriteria(VeterinarianSearchCriteria criteria) {
        return Specification
            .where(criteria.searchField() != null ? filterBySearchField(criteria.searchField()) : null)
            .and(criteria.clinicId() > 0 ? filterByClinic(criteria.clinicId()) : null)
            .and(criteria.providerId() > 0 ? filterByProvider(criteria.providerId()) : null)
            .and(criteria.specialityId() > 0 ? filterBySpeciality(criteria.specialityId()) : null)
            .and(criteria.minDistance() >= 0 && criteria.maxDistance() > 0
                 ? filterByDistance(criteria.minDistance(), criteria.maxDistance())
                 : null)
            .and(criteria.searchField() != null && !criteria.searchField().isBlank()
                 ? filterByFax(criteria.searchField())
                 : null);
    }
}*/

