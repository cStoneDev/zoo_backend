package org.app.zoo.activity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Activity interface to interact with the DB")
public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {
    boolean existsByContractIdAndDateAndTimeAndDescription(int contractId, Date date, Time time, String description);

    @Query("SELECT MONTH(a.date) AS month, COUNT(a) AS count " +
           "FROM Activity a " +
           "WHERE a.date BETWEEN :startDate AND :endDate " +
           "GROUP BY MONTH(a.date)")
    List<Object[]> findActivitiesPerMonthByYear(@Param("startDate") LocalDate startDate, 
                                                 @Param("endDate") LocalDate endDate);

}
