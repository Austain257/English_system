package com.austain.srevice;

import com.austain.domain.dto.Result;

import java.util.List;

public interface IndexListService {
    List<String> getBookNameList();

    Result checkBookExist(String bookName);
}
