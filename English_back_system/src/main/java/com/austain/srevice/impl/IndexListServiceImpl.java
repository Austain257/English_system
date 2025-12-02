package com.austain.srevice.impl;

import com.austain.domain.dto.Result;
import com.austain.mapper.IndexListMapper;
import com.austain.srevice.IndexListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IndexListServiceImpl implements IndexListService {

    @Autowired
    private IndexListMapper indexListMapper;

    @Override
    public List<String> getBookNameList() {
        return indexListMapper.getBookNameList();
    }

    @Override
    public Result checkBookExist(String bookName) {
        List<String> bookNameList = indexListMapper.getBookNameList();
        if (bookNameList.contains(bookName)) {
            return Result.success();
        } else {
            return Result.error("不存在该书本");
        }
    }
}
