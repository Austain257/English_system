package com.austain.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MotivationQuote {
    private Long id;
    private String content;
    private String author;
    private String tag;
    private Integer priority;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
