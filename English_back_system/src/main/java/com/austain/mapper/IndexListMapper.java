package com.austain.mapper;

import com.austain.domain.po.UserBook;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IndexListMapper {

    @Select("""
            SELECT id,user_id,book_name,book_code,description,cover_url,word_count,visibility,status,created_at,updated_at
            FROM user_books
            WHERE user_id in (#{userId},2) AND status = 1
            ORDER BY updated_at DESC
            """)
    List<UserBook> getBooksByUserId(@Param("userId") Long userId);

    @Select("""
            SELECT id,user_id,book_name,book_code,description,cover_url,word_count,visibility,status,created_at,updated_at
            FROM user_books
            WHERE status = 1
            ORDER BY updated_at DESC
            """)
    List<UserBook> getAllBooks();

    @Select("""
            SELECT COUNT(1) FROM user_books
            WHERE status = 1
              AND book_name = #{bookName}
              AND (user_id = #{userId} OR #{isAdmin})
            """)
    int countBookByName(@Param("bookName") String bookName,
                        @Param("userId") Long userId,
                        @Param("isAdmin") boolean isAdmin);
}
