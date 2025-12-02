package com.austain.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordPO {

    private String id;  // 记录的 ID
    private String record;  // 记录内容
    private LocalDateTime createTime;  // 创建时间
    private int selected;  // 是否已选择
    private int alreadyReviewed;   // 是否已复习
    private long toDate;  // 距离今多久
}
