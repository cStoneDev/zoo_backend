package org.app.zoo.animal;

import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
@Schema(description = "Animal interface to interact with the DB")
// JpaSpecificationExecutor -> para realizar consultas con especificaciones
public interface AnimalRepository extends JpaRepository<Animal, Integer>, JpaSpecificationExecutor<Animal>{
    boolean existsByNameAndAgeAndWeightAndBreedIdAndEntryDate(String name, int age, double weight, int breedId, Date entryDate);

    // Ingresos por mes dado un año
    @Query("SELECT MONTH(a.entryDate) AS month, COUNT(a) AS total " +
       "FROM Animal a " +
       "WHERE a.entryDate BETWEEN :startDate AND :endDate " +
       "GROUP BY MONTH(a.entryDate)")
    List<Object[]> findEntriesPerMonthByYear(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Top especies
    @Query("SELECT s.name, COUNT(a) AS count FROM Animal a JOIN a.breed b JOIN b.species s GROUP BY s.name ORDER BY count DESC")
    List<Object[]> findTopSpecies();

}