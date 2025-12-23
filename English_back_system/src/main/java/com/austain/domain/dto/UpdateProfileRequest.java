package com.austain.domain.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String nickname;
    private String email;
    private String avatar;
    /**
     * 用于身份验证的当前密码，后端仅用于校验不会保存
     */
    private String verifyPassword;
}
