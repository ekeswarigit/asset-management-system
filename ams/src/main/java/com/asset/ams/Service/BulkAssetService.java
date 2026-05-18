package com.asset.ams.Service;

import org.springframework.web.multipart.MultipartFile;
import com.asset.ams.dto.Response.BulkUploadResponseDto;

public interface BulkAssetService {

    BulkUploadResponseDto processBulkUpload(MultipartFile file) throws Exception;
}
