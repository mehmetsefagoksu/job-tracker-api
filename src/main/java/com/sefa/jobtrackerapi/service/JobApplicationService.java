package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.sefa.jobtrackerapi.exception.ResourceNotFoundException;
import com.sefa.jobtrackerapi.dto.JobApplicationRequest;

import com.sefa.jobtrackerapi.dto.JobApplicationResponse;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<JobApplicationResponse> getAllApplications() {
        return jobApplicationRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private JobApplication findApplicationById(Long id) {
        return jobApplicationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public JobApplicationResponse getApplicationById(Long id) {
        JobApplication application = findApplicationById(id);

        return toResponse(application);
    }

    public JobApplicationResponse createApplication(
            JobApplicationRequest request
    ) {
        JobApplication application = new JobApplication();

        application.setCompany(request.company());
        application.setPosition(request.position());
        application.setStatus(request.status());
        application.setApplicationDate(request.applicationDate());

        JobApplication savedApplication =
                jobApplicationRepository.save(application);

        return toResponse(savedApplication);
    }

    public JobApplicationResponse updateApplication(
            Long id,
            JobApplicationRequest request
    ) {
        JobApplication existingApplication =
                findApplicationById(id);

        existingApplication.setCompany(request.company());
        existingApplication.setPosition(request.position());
        existingApplication.setStatus(request.status());
        existingApplication.setApplicationDate(request.applicationDate());

        JobApplication updatedApplication =
                jobApplicationRepository.save(existingApplication);

        return toResponse(updatedApplication);
    }

    public void deleteApplication(Long id) {
        JobApplication existingApplication =
                findApplicationById(id);

        jobApplicationRepository.delete(existingApplication);
    }

    private JobApplicationResponse toResponse(
            JobApplication application
    ) {
        return new JobApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getPosition(),
                application.getStatus(),
                application.getApplicationDate()
        );
    }
}