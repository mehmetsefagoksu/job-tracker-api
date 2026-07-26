package com.sefa.jobtrackerapi.controller;

import org.springframework.web.bind.annotation.*;
import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.sefa.jobtrackerapi.service.JobApplicationService;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    public JobApplicationController(
            JobApplicationService jobApplicationService
    ) {
        this.jobApplicationService = jobApplicationService;
    }



    @GetMapping
    public List<JobApplication> getApplications() {
        return jobApplicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getApplicationById(
            @PathVariable Long id
    ) {
        JobApplication application =
                jobApplicationService.getApplicationById(id);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id
    ) {
        boolean deleted =
                jobApplicationService.deleteApplication(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication createApplication(
            @RequestBody JobApplication application
    ) {
        return jobApplicationService.createApplication(application);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplication updatedApplication
    ) {
        JobApplication application =
                jobApplicationService.updateApplication(
                        id,
                        updatedApplication
                );

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(application);
    }

    @GetMapping("/test")
    public String testApi() {
        return "Job Tracker API çalışıyor.";
    }
}