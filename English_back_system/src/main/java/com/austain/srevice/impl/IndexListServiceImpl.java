package com.austain.srevice.impl;

import com.austain.domain.dto.Result;
import com.austain.domain.po.UserBook;
import com.austain.mapper.IndexListMapper;
import com.austain.srevice.IndexListService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexListServiceImpl implements IndexListService {

    @Autowired
    private IndexListMapper indexListMapper;

    @Override
    public List<UserBook> getUserBooks(Long userId, boolean isAdmin) {
        if (isAdmin) {
            return indexListMapper.getAllBooks();
        }
        return indexListMapper.getBooksByUserId(userId);
    }

    @Override
    public Result checkBookExist(Long userId, boolean isAdmin, String bookName) {
        int count = indexListMapper.countBookByName(bookName, userId, isAdmin);
        return count > 0 ? Result.success() : Result.error("不存在该书本");
    }
}
