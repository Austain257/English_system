package com.austain.controller;

import com.austain.domain.dto.Result;
import com.austain.domain.po.AddRequest;
import com.austain.domain.po.Englishs;
import com.austain.domain.dto.WrongbookPageResponse;
import com.austain.domain.po.Sentence;
import com.austain.srevice.EnglishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
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
    public Result addAgainWord(@RequestBody AddRequest request, HttpServletRequest request2){
        Long currentUserId = (Long) request2.getAttribute("currentUserId");
        System.out.println("插入操作已触发");
        
        // 为添加的错词设置用户ID
        request.setUserId(currentUserId);
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
    public Result removeAgainWord(@RequestBody AddRequest request, HttpServletRequest request2){
        Long currentUserId = (Long) request2.getAttribute("currentUserId");
        String currentUserRole = (String) request2.getAttribute("currentUserRole");
        System.out.println("删除操作已触发");
        
        // 验证用户权限：只能删除自己的错词或管理员权限
        if (!"ADMIN".equals(currentUserRole) && !englishService.isWordBelongsToUser(request.getId(), currentUserId)) {
            return Result.error("无权限删除此单词");
        }
        
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

    // 错词本相关接口
    @GetMapping("/wrongbook/all")
    public Result getAllAgainWords(HttpServletRequest request){
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String currentUserRole = (String) request.getAttribute("currentUserRole");
        
        List<Englishs> wrongWords;
        if ("ADMIN".equals(currentUserRole)) {
            // 管理员可以查看所有错词
            wrongWords = englishService.getAllAgainWords();
        } else {
            // 普通用户只能查看自己的错词
            wrongWords = englishService.getAgainWordsByUserId(currentUserId);
        }
        return Result.success(wrongWords);
    }

    /**
     * 错词本分页查询
     * @param page 页码，从1开始
     * @param size 每页条数
     * @param bookname 可选，过滤课本名
     * @param minTimes 可选，错误次数下限，默认0
     */
    @GetMapping("/wrongbook/page")
    public Result getWrongbookPage(@RequestParam Integer page,
                                   @RequestParam Integer size,
                                   @RequestParam(required = false, defaultValue = "all") String bookname,
                                   @RequestParam(required = false, defaultValue = "0") Integer minTimes,
                                   HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String currentUserRole = (String) request.getAttribute("currentUserRole");

        WrongbookPageResponse data = englishService.getWrongbookPage(page, size, currentUserId, currentUserRole, bookname, minTimes);
        return Result.success(data);
    }

    @GetMapping("/wrongbook/book")
    public Result getAgainWordsByBook(@RequestParam(value = "bookname") String bookname, HttpServletRequest request){
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String currentUserRole = (String) request.getAttribute("currentUserRole");
        
        List<Englishs> wrongWords;
        if ("ADMIN".equals(currentUserRole)) {
            wrongWords = englishService.getAgainWordsByBook(bookname);
        } else {
            wrongWords = englishService.getAgainWordsByBookAndUserId(bookname, currentUserId);
        }
        return Result.success(wrongWords);
    }

    @GetMapping("/wrongbook/frequent")
    public Result getFrequentWrongWords(@RequestParam(value = "minTimes", defaultValue = "2") int minTimes, HttpServletRequest request){
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String currentUserRole = (String) request.getAttribute("currentUserRole");
        
        List<Englishs> wrongWords;
        if ("ADMIN".equals(currentUserRole)) {
            wrongWords = englishService.getAgainWordsByTimes(minTimes);
        } else {
            wrongWords = englishService.getAgainWordsByTimesAndUserId(minTimes, currentUserId);
        }
        return Result.success(wrongWords);
    }

    @GetMapping("/wrongbook/books")
    public Result getAgainWordBooks(HttpServletRequest request){
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String currentUserRole = (String) request.getAttribute("currentUserRole");
        
        List<String> books;
        if ("ADMIN".equals(currentUserRole)) {
            books = englishService.getAgainWordBooks();
        } else {
            books = englishService.getAgainWordBooksByUserId(currentUserId);
        }
        return Result.success(books);
    }

    @PostMapping("/wrongbook/increase")
    public Result increaseWordTimes(@RequestParam(value = "id") Long id, HttpServletRequest request){
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String currentUserRole = (String) request.getAttribute("currentUserRole");
        
        // 验证用户权限：只能操作自己的错词或管理员权限
        if (!"ADMIN".equals(currentUserRole) && !englishService.isWordBelongsToUser(id, currentUserId)) {
            return Result.error("无权限操作此单词");
        }
        
        boolean result = englishService.increaseWordTimes(id);
        return result ? Result.success() : Result.error("更新失败");
    }
}
