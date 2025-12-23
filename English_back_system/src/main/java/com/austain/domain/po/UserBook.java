package com.austain.domain.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserBook {
    private Long id;
    private Long userId;
    private String bookName;
    private String bookCode;
    private String description;
    private String coverUrl;
    private Integer wordCount;
    private String visibility;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
