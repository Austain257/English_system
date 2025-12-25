package com.austain.mapper;

import com.austain.domain.po.MotivationQuote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MotivationQuoteMapper {

    @Select("""
            SELECT id, content, author, tag, priority, status, created_at, updated_at
            FROM motivation_quotes
            WHERE status = 1
            ORDER BY priority DESC, RAND()
            LIMIT 1
            """)
    MotivationQuote pickOne();

    @Select("""
            SELECT id, content, author, tag, priority, status, created_at, updated_at
            FROM motivation_quotes
            ORDER BY priority DESC, id DESC
            """)
    List<MotivationQuote> listAll();
}
