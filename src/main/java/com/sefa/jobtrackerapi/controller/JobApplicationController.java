package com.sefa.jobtrackerapi.controller;

import org.springframework.web.bind.annotation.*;
import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.sefa.jobtrackerapi.service.JobApplicationService;
import jakarta.validation.Valid;
import com.sefa.jobtrackerapi.dto.JobApplicationRequest;
import com.sefa.jobtrackerapi.dto.JobApplicationResponse;

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
    public List<JobApplicationResponse> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getApplicationById(
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
    public ResponseEntity<JobApplicationResponse> createApplication(
            @Valid @RequestBody JobApplicationRequest request
    ) {
        JobApplicationResponse createdApplication =
                jobApplicationService.createApplication(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request
    ) {
        return ResponseEntity.ok(
                jobApplicationService.updateApplication(id, request)
        );
    }

    @GetMapping("/test")
    public String testApi() {
        return "Job Tracker API çalışıyor.";
    }
}