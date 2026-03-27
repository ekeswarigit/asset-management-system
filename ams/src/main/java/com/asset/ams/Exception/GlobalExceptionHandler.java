package com.asset.ams.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.asset.ams.dto.ApiResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Runtime Exception
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleRuntime(RuntimeException ex) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ApiResponse<>(false, ex.getMessage(), null, 401, LocalDateTime.now())
    );
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
