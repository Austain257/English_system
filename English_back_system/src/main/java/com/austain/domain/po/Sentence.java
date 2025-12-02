package com.austain.domain.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sentence {

    private String sentenceId;
    private String sentence;
    private String chinese;
    private LocalDateTime createTime;  // 创建时间
}
