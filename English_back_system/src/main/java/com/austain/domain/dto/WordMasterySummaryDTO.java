package com.austain.domain.dto;

import lombok.Data;

@Data
public class WordMasterySummaryDTO {
    private Long bookId;
    private String bookName;
    private Long masteredCount;
    private Long last7DaysMastered;
}
