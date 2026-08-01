package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import com.sefa.jobtrackerapi.repository.JobApplicationRepository;
import com.sefa.jobtrackerapi.dto.ApplicationStatisticsResponse;
import org.springframework.stereotype.Service;

import com.sefa.jobtrackerapi.exception.ResourceNotFoundException;
import com.sefa.jobtrackerapi.dto.JobApplicationRequest;

import com.sefa.jobtrackerapi.dto.JobApplicationResponse;
import com.sefa.jobtrackerapi.model.JobApplicationStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sefa.jobtrackerapi.dto.PageResponse;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public PageResponse<JobApplicationResponse> getAllApplications(
            JobApplicationStatus status,
            String company,
            Pageable pageable
    ) {
        Page<JobApplication> applications;

        boolean hasCompany =
                company != null && !company.isBlank();

        if (status != null && hasCompany) {
            applications =
                    jobApplicationRepository
                            .findByStatusAndCompanyContainingIgnoreCase(
                                    status,
                                    company.trim(),
                                    pageable
                            );
        } else if (status != null) {
            applications =
                    jobApplicationRepository.findByStatus(
                            status,
                            pageable
                    );
        } else if (hasCompany) {
            applications =
                    jobApplicationRepository
                            .findByCompanyContainingIgnoreCase(
                                    company.trim(),
                                    pageable
                            );
        } else {
            applications =
                    jobApplicationRepository.findAll(pageable);
        }

        Page<JobApplicationResponse> responsePage =
                applications.map(this::toResponse);

        return new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isFirst(),
                responsePage.isLast()
        );
    }

    public ApplicationStatisticsResponse getApplicationStatistics() {
        return new ApplicationStatisticsResponse(
                jobApplicationRepository.count(),
                jobApplicationRepository.countByStatus(
                        JobApplicationStatus.APPLIED
                ),
                jobApplicationRepository.countByStatus(
                        JobApplicationStatus.INTERVIEW
                ),
                jobApplicationRepository.countByStatus(
                        JobApplicationStatus.OFFER
                ),
                jobApplicationRepository.countByStatus(
                        JobApplicationStatus.REJECTED
                )
        );
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
