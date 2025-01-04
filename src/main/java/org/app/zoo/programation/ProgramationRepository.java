package org.app.zoo.programation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProgramationRepository extends JpaRepository<Programation, Integer>, JpaSpecificationExecutor<Programation>{
    
}
