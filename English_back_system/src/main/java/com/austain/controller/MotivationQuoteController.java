package com.austain.controller;

import com.austain.domain.dto.Result;
import com.austain.domain.po.MotivationQuote;
import com.austain.srevice.MotivationQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/motivation")
public class MotivationQuoteController {

    @Autowired
    private MotivationQuoteService motivationQuoteService;

    @GetMapping("/quote")
    public Result getQuote() {
        MotivationQuote quote = motivationQuoteService.getRandomQuote();
        if (quote == null) {
            return Result.error("暂未配置鸡汤文，请稍后再试");
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", quote.getId());
        payload.put("content", quote.getContent());
        payload.put("author", quote.getAuthor());
        payload.put("tag", quote.getTag());
        return Result.success(payload);
    }
}
