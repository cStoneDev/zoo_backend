package org.app.zoo.activity;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Activity interface to interact with the DB")
public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {
    boolean existsByContractIdAndDateAndTimeAndDescription(int contractId, Date date, Time time, String description);
}
