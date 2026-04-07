package com.asset.ams.dto;

import com.asset.ams.payload.TransferStatus;

import lombok.Data;

@Data
public class TransferActionDto {

    private TransferStatus status;
}
