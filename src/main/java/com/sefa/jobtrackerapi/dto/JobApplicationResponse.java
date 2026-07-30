package com.sefa.jobtrackerapi.dto;

import com.sefa.jobtrackerapi.model.JobApplicationStatus;

public record JobApplicationResponse(
        Long id,
        String company,
        String position,
        JobApplicationStatus status
) {
}