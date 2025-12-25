package com.austain.controller;

import com.austain.domain.dto.Result;
import com.austain.domain.dto.WordMasteryRequest;
import com.austain.domain.dto.WordMasterySummaryDTO;
import com.austain.srevice.WordMasteryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mastery")
public class WordMasteryController {

    @Autowired
    private WordMasteryService wordMasteryService;

    @PostMapping("/mark")
    public Result markMastery(@RequestBody WordMasteryRequest request, HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        wordMasteryService.markMastery(currentUserId, request);
        return Result.success();
    }

    @PostMapping("/regress")
    public Result regress(@RequestBody WordMasteryRequest request, HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        wordMasteryService.regressMastery(currentUserId, request);
        return Result.success();
    }

    @GetMapping("/summary")
    public Result getSummary(@RequestParam(required = false) Long bookId, HttpServletRequest httpServletRequest) {
        Long currentUserId = (Long) httpServletRequest.getAttribute("currentUserId");
        List<WordMasterySummaryDTO> summaries = wordMasteryService.getSummary(currentUserId, bookId);
        return Result.success(summaries);
    }
}
