package com.austain.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jotting {

    private String id;
    private String english;
    private String chinese;
    private LocalDateTime createTime;  // 创建时间
    private boolean reviewed;
}
