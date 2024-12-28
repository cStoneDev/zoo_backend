package org.app.zoo.provider;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class ProviderSpecification {
    public static Specification<Provider> filterBySearchField(String searchField){
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            // Filtro por nombre
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de provincia
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("province").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de tipo de servicio
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("serviceType").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombre de tipo de proveedor
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("providerType").get("name")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por direccion
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por telefono
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por correo
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchField.toLowerCase() + "%"));

            // Filtro por nombreResponsable
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("responsibleName")), "%" + searchField.toLowerCase() + "%"));

            // Combinar todos los predicados usando OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0])); // combinar múltiples condiciones (predicados) en una consulta.
        };
    }

    public static Specification<Provider> filterByProvince(int provinceId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID de la provincia
            return criteriaBuilder.equal(root.get("province").get("id"), provinceId);
        };
    }

    public static Specification<Provider> filterByServiceType(int serviceTypeId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID del tipo de servicio
            return criteriaBuilder.equal(root.get("serviceType").get("id"), serviceTypeId);
        };
    }
    
    public static Specification<Provider> filterByProviderType(int providerTypeId) {
        return (root, query, criteriaBuilder) -> {
            
            // Filtrar por el ID del tipo de proveedor
            return criteriaBuilder.equal(root.get("providerType").get("id"), providerTypeId);
        };
    }
}
