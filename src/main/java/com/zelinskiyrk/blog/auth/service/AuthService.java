package com.zelinskiyrk.blog.auth.service;

import com.zelinskiyrk.blog.auth.api.request.AuthRequest;
import com.zelinskiyrk.blog.auth.entity.CustomUserDetails;
import com.zelinskiyrk.blog.auth.exceptions.AuthException;
import com.zelinskiyrk.blog.base.service.EmailSenderService;
import com.zelinskiyrk.blog.security.JwtFilter;
import com.zelinskiyrk.blog.security.JwtProvider;
import com.zelinskiyrk.blog.user.exception.UserNotExistException;
import com.zelinskiyrk.blog.user.model.UserDoc;
import com.zelinskiyrk.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final EmailSenderService emailSenderService;

    public CustomUserDetails loadUserByEmail(String email) throws UserNotExistException {
        UserDoc userDoc = userRepository.findByEmail(email).orElseThrow(UserNotExistException::new);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userDoc);
    }

    public String auth(AuthRequest authRequest) throws UserNotExistException, AuthException {
        UserDoc userDoc = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(UserNotExistException::new);
        if (userDoc.getPassword().equals(UserDoc.hexPassword(authRequest.getPassword())) == false){
            userDoc.setFailLogin(userDoc.getFailLogin() + 1);
            userRepository.save(userDoc);

            if (userDoc.getFailLogin() >= 5){
                emailSenderService.sendEmailAlert(userDoc.getEmail());
            }

            throw new AuthException();
        }

        if (userDoc.getFailLogin() > 0){
            userDoc.setFailLogin(0);
            userRepository.save(userDoc);
        }

        String token = jwtProvider.generateToken(authRequest.getEmail());
        return token;
    }

    public static HttpServletRequest getCurrentRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes){
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public UserDoc currentUser() throws AuthException {
        try {
            String email = jwtProvider.getEmailFromToken(JwtFilter.getTokenFromRequest(getCurrentRequest()));
            UserDoc userDoc = userRepository.findByEmail(email).orElseThrow(UserNotExistException::new);
            return userDoc;
        }catch (Exception e){
            throw new AuthException();
        }
    }
}
