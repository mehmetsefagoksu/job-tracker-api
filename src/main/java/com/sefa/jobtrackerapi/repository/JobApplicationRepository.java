package com.sefa.jobtrackerapi.repository;

import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sefa.jobtrackerapi.model.JobApplicationStatus;

import java.util.List;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(
            JobApplicationStatus status
    );
}