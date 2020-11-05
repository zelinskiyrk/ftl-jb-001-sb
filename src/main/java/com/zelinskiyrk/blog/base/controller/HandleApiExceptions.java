package com.zelinskiyrk.blog.base.controller;

import com.zelinskiyrk.blog.base.api.response.ErrorResponse;
import com.zelinskiyrk.blog.user.exception.UserExistException;
import com.zelinskiyrk.blog.user.exception.UserNotExistException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleApiExceptions extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> notFoundException(ChangeSetPersister.NotFoundException ex, WebRequest request){
        return buildResponseEntity(ErrorResponse.of("NotFoundException", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> UserExistException(UserExistException ex, WebRequest request){
        return buildResponseEntity(ErrorResponse.of("UserExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Object> UserNotExistException(UserNotExistException ex, WebRequest request){
        return buildResponseEntity(ErrorResponse.of("UserNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> badRequest(UserNotExistException ex, WebRequest request){
        return buildResponseEntity(ErrorResponse.of("ResponseStatusException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request){
        return buildResponseEntity(ErrorResponse.of("Exception", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
