package com.asset.ams.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asset.ams.Service.BulkAssetService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.Response.BulkUploadResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class BulkAssetController {
    
    private final BulkAssetService bulkAssetService;

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BulkUploadResponseDto>> bulkUpload(
            @RequestParam("file") MultipartFile file) {
        try {
            BulkUploadResponseDto result = bulkAssetService.processBulkUpload(file);
            return ResponseEntity.ok( ApiResponse.<BulkUploadResponseDto>builder()
        .success(true)
        .message("Bulk upload completed")
        .data(result)
        .timestamp(java.time.LocalDateTime.now())
        .build()
      );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
          ApiResponse.<BulkUploadResponseDto>builder()
        .success(false)
        .message(e.getMessage())
        .timestamp(java.time.LocalDateTime.now())
        .build()
     );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
          ApiResponse.<BulkUploadResponseDto>builder()
        .success(false)
        .message("Bulk upload failed: " + e.getMessage())
        .timestamp(java.time.LocalDateTime.now())
        .build()
      );
        }
    }
}
