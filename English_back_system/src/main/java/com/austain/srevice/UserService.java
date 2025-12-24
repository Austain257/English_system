package com.austain.srevice;

import com.austain.domain.dto.LoginRequest;
import com.austain.domain.dto.RegisterRequest;
import com.austain.domain.dto.UpdateProfileRequest;
import com.austain.domain.dto.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    
    /**
     * 用户注册
     */
    String register(RegisterRequest request);
    
    /**
     * 用户登录
     */
    String login(LoginRequest request, String ipAddress, String userAgent);
    
    /**
     * 用户登出
     */
    boolean logout(String token);
    
    /**
     * 根据token获取用户信息
     */
    UserInfo getCurrentUser(String token);
    
    /**
     * 更新用户信息
     */
    boolean updateUserInfo(Long userId, UpdateProfileRequest request);
    
    /**
     * 上传头像
     */
    String uploadAvatar(Long userId, MultipartFile file);
    
    /**
     * 通过URL更新头像
     */
    String updateAvatarByUrl(Long userId, String avatarUrl);
    
    /**
     * 修改密码
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);
    
    /**
     * 验证token是否有效
     */
    boolean validateToken(String token);
    
    /**
     * 根据token获取用户ID
     */
    Long getUserIdByToken(String token);
    
    /**
     * 检查用户名是否存在
     */
    boolean isUsernameExists(String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean isEmailExists(String email);
}
