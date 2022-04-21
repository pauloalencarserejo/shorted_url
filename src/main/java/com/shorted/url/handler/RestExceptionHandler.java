package com.shorted.url.handler;

import java.lang.reflect.Method;

import com.shorted.url.exceptions.BadRequestException;
import com.shorted.url.exceptions.ExceptionResponse;
import com.shorted.url.exceptions.NotFoundException;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler implements AsyncUncaughtExceptionHandler {
    
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.info("Erro async: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handlerBadRequest(
        BadRequestException exception, WebRequest request) {
      ExceptionResponse exceptionDetails =
          new ExceptionResponse(exception.getMessage(), request.getDescription(false));
      return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
  
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerNotFound(
        NotFoundException exception, WebRequest request) {
      ExceptionResponse exceptionDetails =
          new ExceptionResponse(exception.getMessage(), request.getDescription(false));
      return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

}
