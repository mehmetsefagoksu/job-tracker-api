package com.sefa.jobtrackerapi.controller;

import com.sefa.jobtrackerapi.dto.JobApplicationResponse;
import com.sefa.jobtrackerapi.model.JobApplicationStatus;
import com.sefa.jobtrackerapi.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(JobApplicationController.class)
class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobApplicationService jobApplicationService;

    @Test
    void shouldReturnApplicationWhenIdExists() throws Exception {
        JobApplicationResponse response =
                new JobApplicationResponse(
                        1L,
                        "Garanti BBVA Teknoloji",
                        "Java Backend Developer",
                        JobApplicationStatus.APPLIED,
                        LocalDate.of(2026, 7, 30)
                );

        when(jobApplicationService.getApplicationById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/applications/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.company")
                        .value("Garanti BBVA Teknoloji"))
                .andExpect(jsonPath("$.position")
                        .value("Java Backend Developer"))
                .andExpect(jsonPath("$.status")
                        .value("APPLIED"))
                .andExpect(jsonPath("$.applicationDate")
                        .value("2026-07-30"));
    }
    @Test
    void shouldReturnBadRequestWhenCompanyIsBlank() throws Exception {
        String requestBody = """
            {
              "company": "",
              "position": "Java Backend Developer",
              "status": "APPLIED",
              "applicationDate": "2026-07-30"
            }
            """;

        mockMvc.perform(
                        post("/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.company")
                        .value("Şirket adı boş olamaz"));
    }
}