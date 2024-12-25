package org.app.zoo.animal;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

public class AnimalSpecification {

    public static Specification<Animal> filterByBreed(int breedId) {
        return (root, query, criteriaBuilder) -> {

            // Filtrar por el ID de la raza
            return criteriaBuilder.equal(root.get("breed").get("id_breed"), breedId);
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
            return criteriaBuilder.between(root.get("entry_date"), minDateConverted, maxDateConverted);
        };
    }

}
