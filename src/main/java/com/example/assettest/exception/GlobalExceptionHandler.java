package com.example.assettest.exception;

import com.example.assettest.common.ErrorResponse;
import com.example.assettest.common.Result;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception response model source.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Result<ErrorResponse> forbidden(SecurityException ex, HttpServletRequest request) {
        return Result.failure(403, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<ErrorResponse> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return Result.failure(400, "validation failed");
    }
}
