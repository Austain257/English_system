package com.austain.interceptor;

import com.austain.domain.dto.UserInfo;
import com.austain.srevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private UserService userService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域预检请求直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // 提取Token
        String token = extractToken(request);
        
        if (token == null) {
            writeErrorResponse(response, "未登录，请先登录");
            return false;
        }
        
        // 验证Token并获取用户信息
        UserInfo userInfo = userService.getCurrentUser(token);
        if (userInfo == null) {
            writeErrorResponse(response, "登录已过期，请重新登录");
            return false;
        }
        
        // 将用户信息存储在请求属性中，供后续使用
        request.setAttribute("currentUser", userInfo);
        request.setAttribute("currentUserId", userInfo.getId());
        request.setAttribute("currentUserRole", userInfo.getRole());
        
        return true;
    }
    
    /**
     * 从请求中提取Token
     */
    private String extractToken(HttpServletRequest request) {
        // 从Authorization header中获取
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        
        // 从请求参数中获取
        String token = request.getParameter("token");
        if (token != null && !token.trim().isEmpty()) {
            return token;
        }
        
        // 从Cookie中获取
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("auth_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        return null;
    }
    
    /**
     * 写入错误响应
     */
    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        
        String jsonResponse = String.format("{\"code\": 0, \"message\": \"%s\", \"data\": null}", message);
        response.getWriter().write(jsonResponse);
    }
}
