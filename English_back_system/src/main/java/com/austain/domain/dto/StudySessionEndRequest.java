package com.austain.domain.dto;

import lombok.Data;

@Data
public class StudySessionEndRequest {
    private Long sessionId;
    private Integer durationSeconds;
}
