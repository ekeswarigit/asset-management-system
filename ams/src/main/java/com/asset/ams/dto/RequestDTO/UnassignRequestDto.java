package com.asset.ams.dto.RequestDTO;

import lombok.Data;

@Data
public class UnassignRequestDto {
    private Long assetId;

     public Long getAssetId(){
         return assetId;
     }
}
