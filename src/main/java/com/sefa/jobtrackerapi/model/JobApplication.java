package com.sefa.jobtrackerapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "job_applications")


public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Şirket adı boş olamaz")
    @Size(max = 100, message = "Şirket adı en fazla 100 karakter olabilir")
    private String company;

    @NotBlank(message = "Pozisyon boş olamaz")
    @Size(max = 100, message = "Pozisyon en fazla 100 karakter olabilir")
    private String position;

    @NotNull(message = "Durum boş olamaz")
    @Enumerated(EnumType.STRING)
    private JobApplicationStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public JobApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }
}