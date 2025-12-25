package com.austain.domain.dto;

import lombok.Data;

@Data
public class LearningTimeSummaryDTO {
    private String scene;
    private String source;
    private Long totalSeconds;
    private Long todaySeconds;
    private Long last7DaysSeconds;
    private Long last3DaysSeconds;
    private Long last30DaysSeconds;
    private Long last365DaysSeconds;
}
