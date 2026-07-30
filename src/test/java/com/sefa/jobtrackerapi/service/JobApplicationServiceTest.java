package com.sefa.jobtrackerapi.service;

import com.sefa.jobtrackerapi.dto.JobApplicationResponse;
import com.sefa.jobtrackerapi.model.JobApplication;
import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import com.sefa.jobtrackerapi.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.sefa.jobtrackerapi.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sefa.jobtrackerapi.dto.JobApplicationRequest;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @Test
    void shouldReturnApplicationWhenIdExists() {
        JobApplication application = new JobApplication();
        application.setId(1L);
        application.setCompany("Garanti BBVA Teknoloji");
        application.setPosition("Java Backend Developer");
        application.setStatus(JobApplicationStatus.APPLIED);
        application.setApplicationDate(LocalDate.of(2026, 7, 30));

        when(jobApplicationRepository.findById(1L))
                .thenReturn(Optional.of(application));

        JobApplicationResponse response =
                jobApplicationService.getApplicationById(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.company())
                .isEqualTo("Garanti BBVA Teknoloji");
        assertThat(response.position())
                .isEqualTo("Java Backend Developer");
        assertThat(response.status())
                .isEqualTo(JobApplicationStatus.APPLIED);
        assertThat(response.applicationDate())
                .isEqualTo(LocalDate.of(2026, 7, 30));
    }
    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        when(jobApplicationRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> jobApplicationService.getApplicationById(99L)
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ID 99 olan iş başvurusu bulunamadı");
    }

    @Test
    void shouldCreateApplication() {
        JobApplicationRequest request =
                new JobApplicationRequest(
                        "Trendyol",
                        "Java Backend Developer",
                        JobApplicationStatus.APPLIED,
                        LocalDate.of(2026, 7, 30)
                );

        when(jobApplicationRepository.save(any(JobApplication.class)))
                .thenAnswer(invocation -> {
                    JobApplication savedApplication =
                            invocation.getArgument(0);

                    savedApplication.setId(10L);
                    return savedApplication;
                });

        JobApplicationResponse response =
                jobApplicationService.createApplication(request);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.company()).isEqualTo("Trendyol");
        assertThat(response.position())
                .isEqualTo("Java Backend Developer");
        assertThat(response.status())
                .isEqualTo(JobApplicationStatus.APPLIED);
        assertThat(response.applicationDate())
                .isEqualTo(LocalDate.of(2026, 7, 30));

        verify(jobApplicationRepository)
                .save(any(JobApplication.class));
    }
    @Test
    void shouldDeleteApplicationWhenIdExists() {
        JobApplication application = new JobApplication();
        application.setId(1L);
        application.setCompany("Trendyol");
        application.setPosition("Java Backend Developer");
        application.setStatus(JobApplicationStatus.APPLIED);
        application.setApplicationDate(LocalDate.of(2026, 7, 30));

        when(jobApplicationRepository.findById(1L))
                .thenReturn(Optional.of(application));

        jobApplicationService.deleteApplication(1L);

        verify(jobApplicationRepository).findById(1L);
        verify(jobApplicationRepository).delete(application);
    }
}