package com.asset.ams.dto.Response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferHistoryDto {
    private Long thid;
    private LocationDto fromLocation;
    private LocationDto toLocation;
    private UserDto approvedBy;
    private String reason;
    private LocalDateTime transferredAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDto {
        private String locationName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private String userName;
    }
}
