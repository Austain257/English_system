package com.austain.srevice.impl;

import com.austain.mapper.StudyRecordMapper;
import com.austain.srevice.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private StudyRecordMapper studyRecordMapper; // 假设你用 MyBatis

    // 每天 00:00 执行（注意：使用 24 小时制，0 0 0 * * ? 表示 秒 分 时 日 月 周）
    @Scheduled(cron = "0 0 0 * * ?",zone = "Asia/Shanghai")
    @Override
    public void resetScoreAtMidnight() {
        System.out.println("开始重置所有学习记录的 score 为 0...");
        studyRecordMapper.updateAllScoreToZero("study_record");
        studyRecordMapper.updateAllScoreToZero("sentence_record");
        studyRecordMapper.updateAllScoreToZero("daily_record");
        studyRecordMapper.updateAllScoreToZero("word_record");
        studyRecordMapper.updateAllScoreToZero("listening_record");
        System.out.println("score 重置完成！");
    }
}

