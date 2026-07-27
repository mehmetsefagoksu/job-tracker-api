package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.sefa.jobtrackerapi.exception.ResourceNotFoundException;
import com.sefa.jobtrackerapi.dto.JobApplicationRequest;

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
            JobApplicationRequest request
    ) {
        JobApplication application = new JobApplication();

        application.setCompany(request.company());
        application.setPosition(request.position());
        application.setStatus(request.status());

        return jobApplicationRepository.save(application);
    }

    public JobApplication updateApplication(
            Long id,
            JobApplicationRequest request
    ) {
        JobApplication existingApplication =
                getApplicationById(id);

        existingApplication.setCompany(request.company());
        existingApplication.setPosition(request.position());
        existingApplication.setStatus(request.status());

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