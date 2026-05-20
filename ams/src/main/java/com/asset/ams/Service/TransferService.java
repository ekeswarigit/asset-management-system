package com.asset.ams.Service;

import java.util.List;

import com.asset.ams.dto.RequestDTO.TransferRequestDto;
import com.asset.ams.dto.Response.TransferResponseDto;
import com.asset.ams.dto.TransferActionDto;
import com.asset.ams.model.TransferHistory;

public interface TransferService {

    public TransferResponseDto createRequest(TransferRequestDto dto, String username);

    public String processRequest(Long id, TransferActionDto dto, String adminName);

    public List<TransferResponseDto> getAllTransfers();

    public List<TransferResponseDto> getMyTransfers(String username);

    public List<TransferHistory> getLocationHistory(Long assetId);

    public void deleteRequest(Long id);

}
 