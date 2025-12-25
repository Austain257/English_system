package com.austain.controller;

import com.austain.domain.dto.Result;
import com.austain.domain.po.UserBook;
import com.austain.srevice.IndexListService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
public class IndexListController {

    @Autowired
    private IndexListService indexListService;

    @GetMapping
    public Result getBookNameList(HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String role = (String) request.getAttribute("currentUserRole");
        boolean isAdmin = "ADMIN".equals(role);
        List<UserBook> bookNameList = indexListService.getUserBooks(currentUserId, isAdmin);
        return Result.success(bookNameList);
    }

    @GetMapping("/{bookName}")
    public Result checkBookExist(@PathVariable String bookName, HttpServletRequest request){
        Long currentUserId = (Long) request.getAttribute("currentUserId");
        String role = (String) request.getAttribute("currentUserRole");
        boolean isAdmin = "ADMIN".equals(role);
        UserBook book = indexListService.findBook(currentUserId, isAdmin, null, bookName);
        return book != null ? Result.success(book) : Result.error("不存在该书本");
    }
}















