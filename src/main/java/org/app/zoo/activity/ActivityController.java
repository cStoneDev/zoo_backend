package org.app.zoo.activity;

import org.app.zoo.activity.dto.in.ActivityInputDTO;
import org.app.zoo.activity.dto.out.ActivityOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/activity")
@Schema(description = "Activity controller class to handle HTTP requests")
public class ActivityController {
    
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityOutputDTO> createActivity(@RequestBody ActivityInputDTO activity) {
        return new ResponseEntity<>(activityService.createActivity(activity), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ActivityOutputDTO> getAllActivity(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return activityService.getAllActivity(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityOutputDTO> findActivity(@PathVariable int id) {
        ActivityOutputDTO activity = activityService.findById(id);
        return ResponseEntity.ok(activity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityOutputDTO> updateActivity(@PathVariable int id, @RequestBody ActivityInputDTO updatedActivity) {
        ActivityOutputDTO activity = activityService.updateActivity(id, updatedActivity);
        return ResponseEntity.ok(activity);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ActivityOutputDTO>> searchActivity(@RequestBody ActivitySearchCriteria activitySearchCriteria) {
        
        return new ResponseEntity<>(activityService.searchActivity(activitySearchCriteria) , HttpStatus.OK);
        
    }
}
