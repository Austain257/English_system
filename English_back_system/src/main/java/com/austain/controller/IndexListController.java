package com.austain.controller;

import com.austain.domain.dto.Result;
import com.austain.srevice.IndexListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/list")
public class IndexListController {

    @Autowired
    private IndexListService indexListService;

    @GetMapping
    public Result getBookNameList() {
        List<String> bookNameList = indexListService.getBookNameList();
        return Result.success(bookNameList);
    }

    @GetMapping("/{bookName}")
    public Result checkBookExist(@PathVariable String bookName){
        return indexListService.checkBookExist(bookName);
    }
}















