package com.zelinskiyrk.blog.user.api.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
}
