package com.zelinskiyrk.blog.auth.api.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
