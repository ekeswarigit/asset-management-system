package com.asset.ams.Exception;

import org.springframework.web.bind.annotation.*;

import com.asset.ams.dto.ApiResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Runtime Exception
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ApiResponse<String> handleRuntimeException(RuntimeException ex) {
        return ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .errorCode(500)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Handle Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ApiResponse<String> handleNotFound(ResourceNotFoundException ex) {
        return ApiResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .errorCode(404)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
