package com.austain.srevice.impl;

import com.austain.domain.dto.LoginRequest;
import com.austain.domain.dto.RegisterRequest;
import com.austain.domain.dto.UpdateProfileRequest;
import com.austain.domain.dto.UserInfo;
import com.austain.domain.po.User;
import com.austain.domain.po.UserSession;
import com.austain.mapper.UserMapper;
import com.austain.srevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Set<String> ALLOWED_AVATAR_EXT = Set.of("png", "jpg", "jpeg", "webp");
    private static final long AVATAR_MAX_SIZE = 2 * 1024 * 1024; // 2MB
    private static final Path AVATAR_UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "uploads", "avatars");
    
    @Override
    public String register(RegisterRequest request) {
        // 验证用户名是否已存在
        if (isUsernameExists(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 验证邮箱是否已存在
        if (request.getEmail() != null && isEmailExists(request.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }
        
        // 验证密码一致性
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        
        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setRole("USER");
        user.setStatus(1);
        user.setAvatar("/assets/images/default-avatar.png");
        
        int result = userMapper.insertUser(user);
        if (result > 0) {
            return "注册成功";
        } else {
            throw new RuntimeException("注册失败");
        }
    }
    
    @Override
    public String login(LoginRequest request, String ipAddress, String userAgent) {
        // 查找用户
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new RuntimeException("账户已被禁用");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 生成会话令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        
        // 创建会话
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session.setSessionToken(token);
        session.setExpiresAt(LocalDateTime.now().plusDays(7)); // 7天过期
        session.setIpAddress(ipAddress);
        session.setUserAgent(userAgent);
        
        userMapper.insertSession(session);
        
        // 更新最后登录时间
        userMapper.updateLastLoginTime(user.getId(), LocalDateTime.now());
        
        return token;
    }
    
    @Override
    public boolean logout(String token) {
        return userMapper.deleteSession(token) > 0;
    }
    
    @Override
    public UserInfo getCurrentUser(String token) {
        UserSession session = userMapper.findValidSession(token);
        if (session == null) {
            return null;
        }
        
        User user = userMapper.findById(session.getUserId());
        if (user == null) {
            return null;
        }
        
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(user.getEmail());
        userInfo.setNickname(user.getNickname());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setRole(user.getRole());
        userInfo.setLastLoginTime(user.getLastLoginTime());
        userInfo.setCreateTime(user.getCreateTime());
        
        return userInfo;
    }
    
    @Override
    public boolean updateUserInfo(Long userId, UpdateProfileRequest request) {
        if (request == null) {
            throw new RuntimeException("请求参数不能为空");
        }
        User dbUser = userMapper.findById(userId);
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (request.getVerifyPassword() == null || request.getVerifyPassword().isBlank()) {
            throw new RuntimeException("请先输入当前密码以验证身份");
        }
        if (!passwordEncoder.matches(request.getVerifyPassword(), dbUser.getPassword())) {
            throw new RuntimeException("身份验证失败，密码不正确");
        }
        
        String nickname = request.getNickname();
        if (nickname == null || nickname.isBlank()) {
            throw new RuntimeException("昵称不能为空");
        }
        nickname = nickname.trim();
        if (nickname.length() > 30) {
            throw new RuntimeException("昵称长度不能超过30个字符");
        }
        
        String email = request.getEmail();
        if (email != null && !email.isBlank()) {
            email = email.trim();
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new RuntimeException("邮箱格式不正确");
            }
            User emailOwner = userMapper.findByEmail(email);
            if (emailOwner != null && !emailOwner.getId().equals(userId)) {
                throw new RuntimeException("该邮箱已被其他账号使用");
            }
        } else {
            email = null;
        }
        
        String avatar = request.getAvatar();
        if (avatar != null && avatar.length() > 500) {
            throw new RuntimeException("头像地址过长");
        }
        if (avatar == null || avatar.isBlank()) {
            avatar = dbUser.getAvatar();
        }
        
        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setEmail(email);
        
        return userMapper.updateUserInfo(user) > 0;
    }
    
    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        // 更新密码
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(userId, encodedNewPassword) > 0;
    }
    
    @Override
    public boolean validateToken(String token) {
        UserSession session = userMapper.findValidSession(token);
        return session != null;
    }
    
    @Override
    public Long getUserIdByToken(String token) {
        UserSession session = userMapper.findValidSession(token);
        return session != null ? session.getUserId() : null;
    }
    
    @Override
    public boolean isUsernameExists(String username) {
        return userMapper.findByUsername(username) != null;
    }
    
    @Override
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userMapper.findByEmail(email) != null;
    }

    @Override
    public String updateAvatarByUrl(Long userId, String avatarUrl) {
        if (avatarUrl == null || avatarUrl.isBlank()) {
            throw new RuntimeException("请输入头像链接");
        }
        avatarUrl = avatarUrl.trim();
        if (avatarUrl.length() > 500) {
            throw new RuntimeException("头像链接过长");
        }

        try {
            URL parsedUrl = new URL(avatarUrl);
            String protocol = parsedUrl.getProtocol();
            if (!"http".equalsIgnoreCase(protocol) && !"https".equalsIgnoreCase(protocol)) {
                throw new RuntimeException("仅支持 http/https 链接");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("头像链接格式不正确");
        }

        String pathWithoutQuery = avatarUrl.split("\\?")[0];
        int dotIndex = pathWithoutQuery.lastIndexOf('.');
        if (dotIndex == -1) {
            throw new RuntimeException("头像链接需以图片格式结尾");
        }
        String extension = pathWithoutQuery.substring(dotIndex + 1).toLowerCase();
        if (!ALLOWED_AVATAR_EXT.contains(extension)) {
            throw new RuntimeException("仅支持 PNG / JPG / JPEG / WEBP 格式的头像链接");
        }

        userMapper.updateAvatar(userId, avatarUrl);
        return avatarUrl;
    }

    @Override
    public String uploadAvatar(Long userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("请选择需要上传的头像文件");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new RuntimeException("头像大小不能超过2MB");
        }
        
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        }
        extension = extension.toLowerCase();
        if (!ALLOWED_AVATAR_EXT.contains(extension)) {
            throw new RuntimeException("仅支持上传 PNG / JPG / JPEG / WEBP 格式的头像");
        }
        
        try {
            if (!Files.exists(AVATAR_UPLOAD_DIR)) {
                Files.createDirectories(AVATAR_UPLOAD_DIR);
            }
            String fileName = userId + "_" + System.currentTimeMillis() + "." + extension;
            Path targetPath = AVATAR_UPLOAD_DIR.resolve(fileName);
            file.transferTo(targetPath.toFile());
            String relativePath = "/uploads/avatars/" + fileName;
            userMapper.updateAvatar(userId, relativePath);
            return relativePath;
        } catch (IOException e) {
            throw new RuntimeException("头像上传失败，请稍后重试");
        }
    }
}
