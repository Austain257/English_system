package com.austain.controller;

import com.austain.domain.dto.LearningTimeSummaryDTO;
import com.austain.domain.dto.Result;
import com.austain.domain.dto.StudySessionEndRequest;
import com.austain.domain.dto.StudySessionStartRequest;
import com.austain.domain.po.StudySession;
import com.austain.srevice.StudySessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class StudySessionController {

    @Autowired
    private StudySessionService studySessionService;

    @PostMapping("/start")
    public Result startSession(@RequestBody StudySessionStartRequest request, HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        StudySession session = studySessionService.startSession(currentUserId, request);
        return Result.success(session);
    }

    @PostMapping("/end")
    public Result endSession(@RequestBody StudySessionEndRequest request, HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        StudySession session = studySessionService.endSession(currentUserId, request);
        return Result.success(session);
    }

    @GetMapping("/summary")
    public Result getSummary(HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        List<LearningTimeSummaryDTO> summaries = studySessionService.getSummary(currentUserId);
        return Result.success(summaries);
    }
}
