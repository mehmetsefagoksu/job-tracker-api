package com.sefa.jobtrackerapi.repository;

import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByStatus(
            JobApplicationStatus status,
            Pageable pageable
    );
}