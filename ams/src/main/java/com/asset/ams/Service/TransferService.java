package com.asset.ams.Service;

import com.asset.ams.dto.TransferActionDto;
import com.asset.ams.dto.RequestDTO.TransferRequestDto;
import com.asset.ams.dto.Response.TransferResponseDto;

public interface TransferService {

    public TransferResponseDto createRequest(TransferRequestDto dto, String username);

    public String processRequest(Long id, TransferActionDto dto, String adminName);
}
 