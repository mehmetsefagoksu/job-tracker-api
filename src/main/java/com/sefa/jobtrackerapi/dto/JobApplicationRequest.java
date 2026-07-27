package com.sefa.jobtrackerapi.dto;

import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobApplicationRequest(

        @NotBlank(message = "Şirket adı boş olamaz")
        @Size(
                max = 100,
                message = "Şirket adı en fazla 100 karakter olabilir"
        )
        String company,

        @NotBlank(message = "Pozisyon boş olamaz")
        @Size(
                max = 100,
                message = "Pozisyon en fazla 100 karakter olabilir"
        )
        String position,

        @NotNull(message = "Durum boş olamaz")
        JobApplicationStatus status

) {
}