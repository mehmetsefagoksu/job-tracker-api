package com.sefa.jobtrackerapi.dto;

import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
        String company,
        String position,
        JobApplicationStatus status,
        LocalDate applicationDate
) {
}