package com.austain.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private Long id;
    private Long userId;
    private String sessionToken;
    private LocalDateTime expiresAt;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createTime;
}
