package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElse(null);
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

        if (existingApplication == null) {
            return null;
        }

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

    public boolean deleteApplication(Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            return false;
        }

        jobApplicationRepository.deleteById(id);

        return true;
    }
}