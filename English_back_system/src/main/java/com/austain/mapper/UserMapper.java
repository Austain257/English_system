package com.austain.mapper;

import com.austain.domain.po.User;
import com.austain.domain.po.UserSession;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {
    
    // 用户相关操作
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
    
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);
    
    @Insert("INSERT INTO users(username, password, email, nickname, role, status) VALUES(#{username}, #{password}, #{email}, #{nickname}, #{role}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);
    
    @Update("UPDATE users SET nickname = #{nickname}, avatar = #{avatar}, email = #{email}, update_time = NOW() WHERE id = #{id}")
    int updateUserInfo(User user);
    
    @Update("UPDATE users SET password = #{password}, update_time = NOW() WHERE id = #{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    
    @Update("UPDATE users SET last_login_time = #{lastLoginTime}, update_time = NOW() WHERE id = #{id}")
    int updateLastLoginTime(@Param("id") Long id, @Param("lastLoginTime") LocalDateTime lastLoginTime);
    
    @Select("SELECT * FROM users WHERE role = 'ADMIN'")
    List<User> findAllAdmins();
    
    @Select("SELECT * FROM users WHERE status = 1 ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    List<User> findActiveUsers(@Param("offset") int offset, @Param("limit") int limit);
    
    @Select("SELECT COUNT(*) FROM users WHERE status = 1")
    int countActiveUsers();
    
    // 会话相关操作
    @Insert("INSERT INTO user_sessions(user_id, session_token, expires_at, ip_address, user_agent) VALUES(#{userId}, #{sessionToken}, #{expiresAt}, #{ipAddress}, #{userAgent})")
    int insertSession(UserSession session);
    
    @Select("SELECT * FROM user_sessions WHERE session_token = #{token} AND expires_at > NOW()")
    UserSession findValidSession(String token);
    
    @Delete("DELETE FROM user_sessions WHERE session_token = #{token}")
    int deleteSession(String token);
    
    @Delete("DELETE FROM user_sessions WHERE user_id = #{userId}")
    int deleteAllUserSessions(Long userId);
    
    @Delete("DELETE FROM user_sessions WHERE expires_at < NOW()")
    int cleanExpiredSessions();
    
    @Select("SELECT * FROM user_sessions WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<UserSession> findUserSessions(Long userId);
}
