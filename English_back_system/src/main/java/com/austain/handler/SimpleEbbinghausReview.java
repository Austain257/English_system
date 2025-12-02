package com.austain.handler;

import com.austain.domain.po.RecordPO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class SimpleEbbinghausReview {
    
    // 艾宾浩斯复习间隔（天数）
    private static final int[] REVIEW_INTERVALS = {0, 1, 2, 4, 7, 15, 30};
    
    /**
     * 获取需要复习的内容列表
     */
    public List<RecordPO> getReviewItems(List<RecordPO> allRecords, LocalDate today) {
        List<RecordPO> reviewList = new ArrayList<>();
        
        for (RecordPO record : allRecords) {
            LocalDate createDate = record.getCreateTime().toLocalDate(); // 获取创建日期
            long daysBetween = ChronoUnit.DAYS.between(createDate, today);  // 计算两个日期之间的天数差
            
            // 检查是否在复习时间点
            for (int interval : REVIEW_INTERVALS) {
                if (Math.abs(daysBetween - interval) == 0) { // 如果在复习时间点，添加进去返回的列表
                    record.setToDate(daysBetween); // 设置距今时间
                    reviewList.add(record); // 添加到需要复习的列表中
                    break;
                }
            }
        }
        
        return reviewList;
    }
}
