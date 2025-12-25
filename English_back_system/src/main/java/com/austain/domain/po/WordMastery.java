package com.austain.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WordMastery {
    private Long id;
    private Long userId;
    private Long bookId;
    private String wordBank;
    private Long wordId;
    private String wordText;
    private Integer proficiencyScore;
    private Boolean mastered;
    private LocalDateTime firstMasteredTime;
    private LocalDateTime lastMasteredTime;
    private Integer reviewCount;
    private Integer regressionCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
