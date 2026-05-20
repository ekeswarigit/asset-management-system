package com.asset.ams.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.TransferService;
import com.asset.ams.dto.RequestDTO.TransferRequestDto;
import com.asset.ams.dto.Response.TransferResponseDto;
import com.asset.ams.dto.TransferActionDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
     
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransferRequestDto dto,
                                   Principal principal) {
        return ResponseEntity.ok(
                transferService.createRequest(dto, principal.getName())
        );
    }

    // Approve / Reject
    @PutMapping("/{id}/action")
    public ResponseEntity<?> action(@PathVariable Long id,
                                   @RequestBody TransferActionDto dto,
                                   Principal principal) {
        return ResponseEntity.ok(
                transferService.processRequest(id, dto, principal.getName())
        );
    }
    
    @GetMapping
    public ResponseEntity<List<TransferResponseDto>> getAllTransfers() {
        return ResponseEntity.ok(transferService.getAllTransfers());
    }
    
    @GetMapping("/my")
    public ResponseEntity<List<TransferResponseDto>> getMyTransfers(Principal principal) {
        return ResponseEntity.ok(transferService.getMyTransfers(principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        transferService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }
}
