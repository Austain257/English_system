package com.austain.controller;


import com.austain.domain.dto.Result;
import com.austain.domain.po.RecordPO;
import com.austain.srevice.StudyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/study")
public class StudyRecordController {

    @Autowired
    private StudyRecordService studyRecordService;

    /**
     * 添加学习记录
     * @param content 前端传递的参数——请求体，是一个键值对，用map集合接受后根据键值取出来
     * @return 添加成功返回成功，失败返回失败
     */
    @PostMapping("/record")
    public Result addStudyRecord(@RequestBody Map<String,String> content,@RequestParam String table){
        String record = content.get("record");
        System.out.println("添加记录为：" + record);
        int result = studyRecordService.addStudyRecord(record,table);
        return result > 0 ? Result.success() : Result.error("添加失败");
    }

    /**
     * 获取今日需要复习的记录
     * @return 今日需要复习的记录
     */
    @GetMapping("/list")
    public Result getTodayList(@RequestParam String table){
        List<RecordPO> studyRecordList = studyRecordService.getTodayList(table);
        System.out.println(studyRecordList.toString());
        return Result.success(studyRecordList);
    }

    /**
     * 标记已复习
     * @param request 前端传递的参数——请求体，是一个键值对，用map集合接受后根据键值取出来
     * @return 标记成功返回成功，失败返回失败
     */
    @PostMapping("/mark")
    public Result markReviewed(@RequestBody Map<String, List<String>> request,@RequestParam String table){
        List<String> ids = request.get("ids");
        int result = studyRecordService.markReviewed(ids, table);
        return result > 0 ? Result.success() : Result.error("标记失败");
    }

}
