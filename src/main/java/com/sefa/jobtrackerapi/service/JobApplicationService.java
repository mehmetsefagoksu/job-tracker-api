package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobApplicationService {

    private final List<JobApplication> applications = new ArrayList<>();
    private long nextId = 3L;


    public JobApplicationService() {
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
    public List<JobApplication> getAllApplications() {
        return applications;
    }

    public JobApplication getApplicationById(Long id) {
        for (JobApplication application : applications) {
            if (application.getId().equals(id)) {
                return application;
            }
        }

        return null;
    }
    public JobApplication createApplication(JobApplication application) {
        application.setId(nextId);
        nextId++;

        applications.add(application);

        return application;
    }
    public JobApplication updateApplication(
            Long id,
            JobApplication updatedApplication
    ) {
        JobApplication existingApplication = getApplicationById(id);

        if (existingApplication == null) {
            return null;
        }

        existingApplication.setCompany(updatedApplication.getCompany());
        existingApplication.setPosition(updatedApplication.getPosition());
        existingApplication.setStatus(updatedApplication.getStatus());

        return existingApplication;
    }
    public boolean deleteApplication(Long id) {
        return applications.removeIf(
                application -> application.getId().equals(id)
        );
    }
}