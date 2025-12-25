package com.austain.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudySession {
    private Long id;
    private Long userId;
    private String scene;
    private String source;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationSeconds;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
