package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.sefa.jobtrackerapi.exception.ResourceNotFoundException;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll();
    }

    public JobApplication getApplicationById(Long id) {
        return jobApplicationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public JobApplication createApplication(
            JobApplication application
    ) {
        application.setId(null);

        return jobApplicationRepository.save(application);
    }

    public JobApplication updateApplication(
            Long id,
            JobApplication updatedApplication
    ) {
        JobApplication existingApplication =
                getApplicationById(id);

        existingApplication.setCompany(
                updatedApplication.getCompany()
        );
        existingApplication.setPosition(
                updatedApplication.getPosition()
        );
        existingApplication.setStatus(
                updatedApplication.getStatus()
        );

        return jobApplicationRepository.save(
                existingApplication
        );
    }

    public void deleteApplication(Long id) {
        JobApplication existingApplication =
                getApplicationById(id);

        jobApplicationRepository.delete(existingApplication);
    }
}