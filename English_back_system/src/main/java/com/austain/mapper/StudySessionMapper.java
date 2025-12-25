package com.austain.mapper;

import com.austain.domain.dto.LearningTimeSummaryDTO;
import com.austain.domain.po.StudySession;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudySessionMapper {

    @Insert("""
            INSERT INTO study_sessions (user_id, scene, source, start_time, status)
            VALUES (#{userId}, #{scene}, #{source}, #{startTime}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSession(StudySession session);

    @Update("""
            UPDATE study_sessions
            SET end_time = #{endTime},
                duration_seconds = #{durationSeconds},
                status = #{status},
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int updateSession(StudySession session);

    @Select("""
            SELECT id, user_id, scene, source, start_time, end_time, duration_seconds, status, created_at, updated_at
            FROM study_sessions
            WHERE id = #{id} AND user_id = #{userId}
            """)
    StudySession findById(@Param("id") Long id, @Param("userId") Long userId);

    @Select("""
            SELECT id, user_id, scene, source, start_time, end_time, duration_seconds, status, created_at, updated_at
            FROM study_sessions
            WHERE user_id = #{userId} AND status = 'RUNNING'
            ORDER BY start_time DESC
            LIMIT 1
            """)
    StudySession findRunningSession(@Param("userId") Long userId);

    @Select("""
            SELECT
                ss.scene AS scene,
                ss.source AS source,
                SUM(duration_value) AS totalSeconds,
                SUM(CASE WHEN DATE(ss.updated_at) = CURRENT_DATE THEN duration_value ELSE 0 END) AS todaySeconds,
                SUM(CASE WHEN ss.updated_at >= DATE_SUB(NOW(), INTERVAL 3 DAY) THEN duration_value ELSE 0 END) AS last3DaysSeconds,
                SUM(CASE WHEN ss.updated_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN duration_value ELSE 0 END) AS last7DaysSeconds,
                SUM(CASE WHEN ss.updated_at >= DATE_SUB(NOW(), INTERVAL 30 DAY) THEN duration_value ELSE 0 END) AS last30DaysSeconds,
                SUM(CASE WHEN ss.updated_at >= DATE_SUB(NOW(), INTERVAL 365 DAY) THEN duration_value ELSE 0 END) AS last365DaysSeconds
            FROM (
                     SELECT
                         id,
                         user_id,
                         scene,
                         source,
                         status,
                         updated_at,
                         IFNULL(duration_seconds,
                                TIMESTAMPDIFF(SECOND, start_time, IFNULL(end_time, NOW()))
                         ) AS duration_value
                     FROM study_sessions
                     WHERE user_id = #{userId}
                       AND status IN ('COMPLETED', 'TIMEOUT')
                 ) ss
            GROUP BY ss.scene, ss.source
            ORDER BY todaySeconds DESC, totalSeconds DESC
            """)
    List<LearningTimeSummaryDTO> getLearningTimeSummary(@Param("userId") Long userId);

    @Update("""
            UPDATE study_sessions
            SET status = #{status},
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Update("""
            UPDATE study_sessions
            SET duration_seconds = TIMESTAMPDIFF(SECOND, start_time, NOW()),
                end_time = NOW(),
                status = 'TIMEOUT',
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int markTimeout(@Param("id") Long id);
}
