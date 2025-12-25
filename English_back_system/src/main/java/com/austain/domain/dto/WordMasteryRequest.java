package com.austain.domain.dto;

import lombok.Data;

@Data
public class WordMasteryRequest {
    private Long bookId;
    private String bookName;
    private String bookCode;
    private Long wordId;
    private String wordText;
    private String wordBank;
    private Integer proficiencyScore;
    private Boolean mastered = Boolean.TRUE;
}
