package com.austain.mapper;

import com.austain.domain.dto.WordMasterySummaryDTO;
import com.austain.domain.po.WordMastery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WordMasteryMapper {

    @Insert("""
            INSERT INTO word_mastery (
                user_id, book_id, word_bank, word_id, word_text,
                proficiency_score, mastered, first_mastered_time,
                last_mastered_time, review_count, regression_count
            ) VALUES (
                #{userId}, #{bookId}, #{wordBank}, #{wordId}, #{wordText},
                #{proficiencyScore}, #{mastered}, #{firstMasteredTime},
                #{lastMasteredTime}, #{reviewCount}, #{regressionCount}
            )
            ON DUPLICATE KEY UPDATE
                proficiency_score = COALESCE(VALUES(proficiency_score), word_mastery.proficiency_score),
                mastered = VALUES(mastered),
                last_mastered_time = VALUES(last_mastered_time),
                review_count = word_mastery.review_count + 1,
                first_mastered_time = COALESCE(word_mastery.first_mastered_time, VALUES(first_mastered_time)),
                updated_at = NOW()
            """)
    int upsert(WordMastery mastery);

    @Update("""
            UPDATE word_mastery
            SET mastered = 0,
                regression_count = regression_count + 1,
                updated_at = NOW()
            WHERE user_id = #{userId}
              AND book_id = #{bookId}
              AND (
                    (word_id IS NOT NULL AND word_id = #{wordId})
                    OR (word_id IS NULL AND #{wordId} IS NULL AND word_text = #{wordText})
                  )
            """)
    int regress(@Param("userId") Long userId,
                @Param("bookId") Long bookId,
                @Param("wordId") Long wordId,
                @Param("wordText") String wordText);

    @Select("""
            SELECT
                wm.book_id AS bookId,
                COALESCE(ub.book_name, '未命名词书') AS bookName,
                SUM(CASE WHEN wm.mastered = 1 THEN 1 ELSE 0 END) AS masteredCount,
                SUM(CASE WHEN wm.mastered = 1 AND wm.updated_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN 1 ELSE 0 END) AS last7DaysMastered
            FROM word_mastery wm
                     LEFT JOIN user_books ub ON wm.book_id = ub.id
            WHERE wm.user_id = #{userId}
            GROUP BY wm.book_id, ub.book_name
            ORDER BY masteredCount DESC
            """)
    List<WordMasterySummaryDTO> getSummary(@Param("userId") Long userId);
}
