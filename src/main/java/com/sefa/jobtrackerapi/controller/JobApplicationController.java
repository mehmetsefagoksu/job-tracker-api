package com.sefa.jobtrackerapi.controller;

import org.springframework.web.bind.annotation.*;
import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {
    private final List<JobApplication> applications = new ArrayList<>();
    private long nextId = 3L;

    public JobApplicationController() {

        JobApplication firstApplication = new JobApplication();
        firstApplication.setId(1L);
        firstApplication.setCompany("Example Technology");
        firstApplication.setPosition("Junior Software Engineer");
        firstApplication.setStatus("APPLIED");

        JobApplication secondApplication = new JobApplication();
        secondApplication.setId(2L);
        secondApplication.setCompany("Sample Bank");
        secondApplication.setPosition("Java Developer");
        secondApplication.setStatus("INTERVIEW");

        applications.add(firstApplication);
        applications.add(secondApplication);
    }

    @GetMapping
    public List<JobApplication> getApplications() {
        return applications;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getApplicationById(
            @PathVariable Long id
    ) {
        for (JobApplication application : applications) {

            if (application.getId().equals(id)) {
                return ResponseEntity.ok(application);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id
    ) {
        for (JobApplication application : applications) {

            if (application.getId().equals(id)) {
                applications.remove(application);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication createApplication(
            @RequestBody JobApplication application
    ) {
        application.setId(nextId);
        nextId++;

        applications.add(application);

        return application;
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplication updatedApplication
    ) {
        for (JobApplication application : applications) {

            if (application.getId().equals(id)) {
                application.setCompany(updatedApplication.getCompany());
                application.setPosition(updatedApplication.getPosition());
                application.setStatus(updatedApplication.getStatus());

                return ResponseEntity.ok(application);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/test")
    public String testApi() {
        return "Job Tracker API çalışıyor.";
    }
}