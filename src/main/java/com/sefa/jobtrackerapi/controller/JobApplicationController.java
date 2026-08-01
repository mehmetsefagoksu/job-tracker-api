package com.sefa.jobtrackerapi.controller;

import org.springframework.data.domain.Page;
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
import org.springframework.data.domain.Pageable;

import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import org.springframework.web.bind.annotation.RequestParam;
import com.sefa.jobtrackerapi.dto.PageResponse;
import com.sefa.jobtrackerapi.dto.ApplicationStatisticsResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    public JobApplicationController(
            JobApplicationService jobApplicationService
    ) {
        this.jobApplicationService = jobApplicationService;
    }



    @GetMapping
    public PageResponse<JobApplicationResponse> getAllApplications(
            @RequestParam(required = false)
            JobApplicationStatus status,

            @RequestParam(required = false)
            String company,

            Pageable pageable
    ) {
        return jobApplicationService.getAllApplications(
                status,
                company,
                pageable
        );
    }

    @GetMapping("/statistics")
    public ApplicationStatisticsResponse getApplicationStatistics() {
        return jobApplicationService.getApplicationStatistics();
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
}
