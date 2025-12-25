package com.austain.controller;

import com.austain.domain.dto.LearningTimeSummaryDTO;
import com.austain.domain.dto.Result;
import com.austain.domain.dto.WordMasterySummaryDTO;
import com.austain.srevice.StudySessionService;
import com.austain.srevice.WordMasteryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/learning")
public class LearningStatsController {

    @Autowired
    private StudySessionService studySessionService;

    @Autowired
    private WordMasteryService wordMasteryService;

    @GetMapping("/dashboard")
    public Result getDashboard(@RequestParam(required = false) Long bookId,
                               HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        List<LearningTimeSummaryDTO> timeSummary = studySessionService.getSummary(currentUserId);
        List<WordMasterySummaryDTO> masterySummary = wordMasteryService.getSummary(currentUserId, bookId);
        Map<String, Object> response = new HashMap<>();
        response.put("learningTime", timeSummary);
        response.put("wordMastery", masterySummary);
        return Result.success(response);
    }
}
