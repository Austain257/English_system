package com.austain.srevice;

import com.austain.domain.po.RecordPO;

import java.util.List;

public interface StudyRecordService {

    /**
     * 添加学习记录
     * @param studyRecord 前端传递的学习记录，是一个字符串
     * @return 添加成功返回1，添加失败返回0
     */
    int addStudyRecord(String studyRecord,String table);

    /**
     * 获取今日需要复习的记录
     * @return 今日需要复习的记录列表
     */
    List<RecordPO> getTodayList(String table);

    /**
     * 标记已复习的记录
     * @param ids 需要标记的记录的 ID 列表
     * @return 标记成功返回1，标记失败返回0
     */
    int markReviewed(List<String> ids,String table);
}
