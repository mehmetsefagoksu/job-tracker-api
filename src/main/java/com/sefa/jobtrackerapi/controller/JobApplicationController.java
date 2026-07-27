package com.sefa.jobtrackerapi.controller;

import org.springframework.web.bind.annotation.*;
import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.sefa.jobtrackerapi.service.JobApplicationService;
import jakarta.validation.Valid;


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
        return ResponseEntity.ok(
                jobApplicationService.getApplicationById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id
    ) {
        jobApplicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication createApplication(
            @Valid @RequestBody JobApplication application
    ) {
        return jobApplicationService.createApplication(application);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplication updatedApplication
    ) {
        return ResponseEntity.ok(
                jobApplicationService.updateApplication(
                        id,
                        updatedApplication
                )
        );
    }

    @GetMapping("/test")
    public String testApi() {
        return "Job Tracker API çalışıyor.";
    }
}