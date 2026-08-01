package com.sefa.jobtrackerapi.dto;

public record ApplicationStatisticsResponse(
        long total,
        long applied,
        long interview,
        long offer,
        long rejected
) {
}
