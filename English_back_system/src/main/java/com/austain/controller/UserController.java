package com.austain.controller;

import com.austain.domain.dto.*;
import com.austain.srevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest request) {
        try {
            String message = userService.register(request);
            return Result.success(message);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 上传头像
     */
    @PostMapping("/avatar")
    public Result uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        try {
            if (avatar == null || avatar.isEmpty()) {
                return Result.error("请上传有效的头像文件");
            }
            
            String token = extractToken(request);
            if (token == null) {
                return Result.error("未登录");
            }
            
            Long userId = userService.getUserIdByToken(token);
            if (userId == null) {
                return Result.error("登录已过期");
            }
            
            String avatarUrl = userService.uploadAvatar(userId, avatar);
            return Result.success(avatarUrl);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 通过URL更新头像
     */
    @PostMapping("/avatar/url")
    public Result updateAvatarByUrl(@RequestBody AvatarUrlRequest avatarUrlRequest, HttpServletRequest request) {
        try {
            if (avatarUrlRequest == null || avatarUrlRequest.getUrl() == null || avatarUrlRequest.getUrl().isBlank()) {
                return Result.error("请输入有效的头像链接");
            }
            
            String token = extractToken(request);
            if (token == null) {
                return Result.error("未登录");
            }
            
            Long userId = userService.getUserIdByToken(token);
            if (userId == null) {
                return Result.error("登录已过期");
            }
            
            String avatarUrl = userService.updateAvatarByUrl(userId, avatarUrlRequest.getUrl());
            return Result.success(avatarUrl);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            String ipAddress = getClientIpAddress(httpRequest);
            String userAgent = httpRequest.getHeader("User-Agent");
            String token = userService.login(request, ipAddress, userAgent);
            return Result.success(token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        try {
            String token = extractToken(request);
            if (token != null) {
                userService.logout(token);
            }
            return Result.success("登出成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result getCurrentUserInfo(HttpServletRequest request) {
        try {
            String token = extractToken(request);
            if (token == null) {
                return Result.error("未登录");
            }
            
            UserInfo userInfo = userService.getCurrentUser(token);
            if (userInfo == null) {
                return Result.error("登录已过期");
            }
            
            return Result.success(userInfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public Result updateUserInfo(@RequestBody UpdateProfileRequest userInfo, HttpServletRequest request) {
        try {
            String token = extractToken(request);
            if (token == null) {
                return Result.error("未登录");
            }
            
            Long userId = userService.getUserIdByToken(token);
            if (userId == null) {
                return Result.error("登录已过期");
            }
            
            boolean success = userService.updateUserInfo(userId, userInfo);
            return success ? Result.success("更新成功") : Result.error("更新失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result changePassword(@RequestBody ChangePasswordRequest request, HttpServletRequest httpRequest) {
        try {
            String token = extractToken(httpRequest);
            if (token == null) {
                return Result.error("未登录");
            }
            
            Long userId = userService.getUserIdByToken(token);
            if (userId == null) {
                return Result.error("登录已过期");
            }
            
            boolean success = userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
            return success ? Result.success("密码修改成功") : Result.error("密码修改失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 验证用户名是否可用
     */
    @GetMapping("/check-username")
    public Result checkUsername(@RequestParam String username) {
        try {
            boolean exists = userService.isUsernameExists(username);
            return Result.success(!exists ? "用户名可用" : "用户名已存在");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 验证邮箱是否可用
     */
    @GetMapping("/check-email")
    public Result checkEmail(@RequestParam String email) {
        try {
            boolean exists = userService.isEmailExists(email);
            return Result.success(!exists ? "邮箱可用" : "邮箱已被注册");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 从请求中提取Token
     */
    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        
        // 也支持从Cookie中获取
        String token = request.getParameter("token");
        if (token != null) {
            return token;
        }
        
        return null;
    }
    
    /**
     * 获取客户端IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
