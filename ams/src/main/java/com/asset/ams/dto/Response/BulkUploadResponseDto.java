package com.asset.ams.dto.Response;

import java.util.List;
import lombok.Data;

@Data
public class BulkUploadResponseDto {
    
    private int totalRows;
    private int successCount;
    private int failedCount;
    private List<RowErrorDto> errors;

    @Data
    public static class RowErrorDto {
        private int row;
        private String reason;

        public RowErrorDto(int row, String reason) {
            this.row = row;
            this.reason = reason;
        }
    }
}
