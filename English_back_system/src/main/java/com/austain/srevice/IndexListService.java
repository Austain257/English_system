package com.austain.srevice;

import com.austain.domain.dto.Result;
import com.austain.domain.po.UserBook;
import java.util.List;

public interface IndexListService {
    List<UserBook> getUserBooks(Long userId, boolean isAdmin);

    Result checkBookExist(Long userId, boolean isAdmin, String bookName);

    UserBook findBook(Long userId, boolean isAdmin, Long bookId, String bookName);
}
