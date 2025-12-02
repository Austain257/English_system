package com.austain.controller;

import com.austain.domain.dto.Result;
import com.austain.domain.po.AddRequest;
import com.austain.domain.po.Englishs;
import com.austain.domain.po.Sentence;
import com.austain.srevice.EnglishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/english")
public class EnglishController {

    @Autowired
    private EnglishService englishService;

    @GetMapping("/list")
    public Result getEnglishList(@RequestParam(value = "start") String start,
                                 @RequestParam(value = "end") String end,
                                 @RequestParam(value = "bookname", required = false) String bookname){
        List<Englishs> englishs = englishService.getEnglishList(start,end,bookname);
        return Result.success(englishs);
    }

    @GetMapping("/recite")
    public Result reciteEnglishList(@RequestParam(value = "start") String start,
                                    @RequestParam(value = "end") String end,
                                    @RequestParam(value = "bookname", required = false) String bookname){
        List<Englishs> englishs = englishService.getEnglishList(start,end,bookname);
        return Result.success(englishs);
    }

    @PostMapping("/add")
    public Result addAgainWord(@RequestBody AddRequest request){
        System.out.println("插入操作已触发");
        boolean result = englishService.addAgainWord(request);
        return result ? Result.success() : Result.error("添加失败");
    }

    @PostMapping("/finalAdd")
    public Result finallyAddAgainWord(@RequestBody AddRequest request){
        System.out.println("插入操作已触发");
        boolean result = englishService.FinalAddAgainWord(request);
        return result ? Result.success() : Result.error("添加失败");
    }

    @PostMapping("/remove")
    public Result removeAgainWord(@RequestBody AddRequest request){
        System.out.println("删除操作已触发");
        boolean result = englishService.removeAgainWord(request);
        return result ? Result.success() : Result.error("删除失败");
    }

    @GetMapping("/sentence")
    public Result getSentenceList(@RequestParam(value = "start") String start,
                                   @RequestParam(value = "end") String end){
        List<Sentence> sentenceList = englishService.getSentenceList(start,end);
        return Result.success(sentenceList);
    }

    @GetMapping("/worddictation")
    public Result getDictationList(@RequestParam(value = "start") String start,
                                   @RequestParam(value = "end") String end){
        List<Englishs> dactationList = englishService.getEnglishList(start,end,"englishword575");
        return Result.success(dactationList);
    }
}
