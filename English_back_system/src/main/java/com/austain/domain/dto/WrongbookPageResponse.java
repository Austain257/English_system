package com.austain.domain.dto;

import com.austain.domain.po.Englishs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 错词本分页响应，附带统计数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongbookPageResponse implements Serializable {

    /** 总记录数 */
    private long total;

    /** 当前页数据 */
    private List<Englishs> records;

    /** 高频错词数量（times>=3） */
    private long frequentCount;

    /** 课本数 */
    private long bookCount;

    /** 今日新增数 */
    private long todayCount;
}
