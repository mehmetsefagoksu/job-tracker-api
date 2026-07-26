package com.sefa.jobtrackerapi.repository;

import com.sefa.jobtrackerapi.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {
}