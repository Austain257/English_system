package com.austain.controller;

// TODO
import com.austain.domain.po.Englishs;
import com.austain.srevice.EnglishService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/article")
public class TestController {

    private final ChatClient textClient;

    // 通过构造函数注入ChatClient
    public TestController(ChatClient textClient) {
        this.textClient = textClient;
    }

    @Autowired
    private EnglishService englishService;

    @GetMapping(value = "/generation", produces = "text/html;charset=utf-8")
    public Flux<String> generateText(
            @RequestParam("beginIndex") String beginIndex,
            @RequestParam("endIndex") String endIndex,
            @RequestParam("bookName") String bookName) {

        System.out.println("开始查数据库，当前需求为" + beginIndex + "到" + endIndex);
        List<Englishs> selectedWords = englishService.getEnglishList(beginIndex, endIndex,bookName);
        System.out.println("开始生成文章");

        return textClient.prompt()
//                .user("article ache astronaut without acid specify daylight poetry seven fetch immigrant")
                .user(selectedWords.toString())
                .stream()
                .content();
    }
}