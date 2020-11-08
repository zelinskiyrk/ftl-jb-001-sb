package com.zelinskiyrk.blog.security;

import com.zelinskiyrk.blog.auth.entity.CustomUserDetails;
import com.zelinskiyrk.blog.auth.service.AuthService;
import com.zelinskiyrk.blog.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@Log
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION = "Authorization";
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtProvider.validateToken(token)){
                String email = jwtProvider.getEmailFromToken(token);
                CustomUserDetails customUserDetails = null;
                customUserDetails = authService.loadUserByEmail(email);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        catch (UserNotExistException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static String getTokenFromRequest(HttpServletRequest request){
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }
}
