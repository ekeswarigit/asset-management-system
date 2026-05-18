package com.asset.ams.Service.Impl;

import com.asset.ams.dto.Response.BulkUploadResponseDto;
import com.asset.ams.dto.Response.BulkUploadResponseDto.RowErrorDto;
import com.asset.ams.model.Asset;
import com.asset.ams.model.AssetType;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;
import com.asset.ams.Repository.AssetRepository;
import com.asset.ams.Repository.AssetTypeRepository;
import com.asset.ams.Repository.LocationRepository;
import com.asset.ams.Service.BulkAssetService;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BulkAssetServiceImpl implements BulkAssetService {

    private final AssetRepository        assetRepository;
    private final AssetTypeRepository    assetTypeRepository;
    private final LocationRepository     locationRepository;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ── Main entry point ──────────────────────────────────────────────────────
    @Override
    public BulkUploadResponseDto processBulkUpload(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();

        if (filename != null && filename.endsWith(".xlsx")) {
            return processExcel(file);
        } else if (filename != null && filename.endsWith(".csv")) {
            return processCsv(file);
        } else {
            throw new IllegalArgumentException("Only .xlsx and .csv files are supported");
        }
    }

    // ── Excel processing ──────────────────────────────────────────────────────
    private BulkUploadResponseDto processExcel(MultipartFile file) throws Exception {
        List<RowErrorDto> errors  = new ArrayList<>();
        int successCount          = 0;
        int rowIndex              = 1;

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (rowIndex == 1) { rowIndex++; continue; } // skip header

                // skip completely empty rows
                if (isRowEmpty(row)) { rowIndex++; continue; }

                try {
                    Asset asset = mapExcelRowToAsset(row, rowIndex);
                    assetRepository.save(asset);
                    successCount++;
                } catch (Exception e) {
                    errors.add(new RowErrorDto(rowIndex, e.getMessage()));
                }
                rowIndex++;
            }
        }

        return buildResponse(rowIndex - 2, successCount, errors);
    }

    // ── CSV processing ────────────────────────────────────────────────────────
    private BulkUploadResponseDto processCsv(MultipartFile file) throws Exception {
        List<RowErrorDto> errors  = new ArrayList<>();
        int successCount          = 0;
        int rowIndex              = 1;

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(file.getInputStream()))) {

            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                if (isHeader) { isHeader = false; continue; } // skip header
                rowIndex++;

                try {
                    Asset asset = mapCsvRowToAsset(line, rowIndex);
                    assetRepository.save(asset);
                    successCount++;
                } catch (Exception e) {
                    errors.add(new RowErrorDto(rowIndex, e.getMessage()));
                }
            }
        }

        return buildResponse(rowIndex - 1, successCount, errors);
    }

    // ── Map Excel row → Asset ─────────────────────────────────────────────────
    private Asset mapExcelRowToAsset(Row row, int rowIndex) {
    Asset asset = new Asset();

    // Col 0 — assetName
    String assetName = getCellString(row, 0);
    if (assetName == null || assetName.isBlank())
        throw new IllegalArgumentException("Asset name is required");
    if (assetName.length() < 3)
        throw new IllegalArgumentException("Asset name must be at least 3 characters");
    asset.setAssetName(assetName);

    // Col 1 — brand
    asset.setBrand(getCellString(row, 1));

    // Col 2 — model
    asset.setModel(getCellString(row, 2));

    // Col 3 — typeId ✅
    String typeIdStr = getCellString(row, 3);
    if (typeIdStr == null || typeIdStr.isBlank())
        throw new IllegalArgumentException("Type ID is required");
    try {
        Long typeId = Long.parseLong(typeIdStr.trim());
        AssetType assetType = assetTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "AssetType not found for ID: " + typeId));
        asset.setAssetType(assetType);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid typeId: " + typeIdStr);
    }

    // Col 4 — status ✅
    String statusStr = getCellString(row, 4);
    if (statusStr != null && !statusStr.isBlank()) {
        try { asset.setStatus(AssetStatus.valueOf(statusStr.trim().toUpperCase())); }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + statusStr +
                    ". Valid: AVAILABLE, ASSIGNED, RETIRED");
        }
    }

    // Col 5 — assetCondition ✅
    String condStr = getCellString(row, 5);
    if (condStr != null && !condStr.isBlank()) {
        try { asset.setAssetCondition(AssetCondition.valueOf(condStr.trim().toUpperCase())); }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid condition: " + condStr +
                    ". Valid: GOOD, FAIR, POOR");
        }
    }

    // Col 6 — purchaseDate
    asset.setPurchaseDate(parseDate(getCellString(row, 6)));

    // Col 7 — warrantyExpiry
    asset.setWarrantyExpiry(parseDate(getCellString(row, 7)));

    // Col 8 — cost
    String costStr = getCellString(row, 8);
    if (costStr != null && !costStr.isBlank()) {
        try { asset.setCost(new BigDecimal(costStr.trim())); }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cost: " + costStr);
        }
    }

    // Col 9 — notes
    asset.setNotes(getCellString(row, 9));

    // Col 10 — locationId (optional)
    String locationIdStr = getCellString(row, 10);
    if (locationIdStr != null && !locationIdStr.isBlank()) {
        try {
            Long locationId = Long.parseLong(locationIdStr.trim());
            locationRepository.findById(locationId).ifPresent(asset::setLocation);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid locationId: " + locationIdStr);
        }
    }

    return asset;
}

    // ── Map CSV row → Asset ───────────────────────────────────────────────────
    private Asset mapCsvRowToAsset(String[] cols, int rowIndex) {
        if (cols.length < 10)
            throw new IllegalArgumentException("Row has too few columns (expected 10+)");

        Asset asset = new Asset();

        // assetName
        String assetName = clean(cols[0]);
        if (assetName.isBlank())
            throw new IllegalArgumentException("Asset name is required");
        if (assetName.length() < 3)
            throw new IllegalArgumentException("Asset name must be at least 3 characters");
        asset.setAssetName(assetName);

        // brand, model
        asset.setBrand(clean(cols[1]));
        asset.setModel(clean(cols[2]));

        // dates
        asset.setPurchaseDate(parseDate(clean(cols[3])));
        asset.setWarrantyExpiry(parseDate(clean(cols[4])));

        // cost
        String costStr = clean(cols[5]);
        if (!costStr.isBlank()) {
            try { asset.setCost(new BigDecimal(costStr)); }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid cost value: " + costStr);
            }
        }

        // status
        String statusStr = clean(cols[6]);
        if (!statusStr.isBlank()) {
            try { asset.setStatus(AssetStatus.valueOf(statusStr.toUpperCase())); }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + statusStr +
                        ". Valid values: AVAILABLE, ASSIGNED, RETIRED");
            }
        }

        // condition
        String condStr = clean(cols[7]);
        if (!condStr.isBlank()) {
            try { asset.setAssetCondition(AssetCondition.valueOf(condStr.toUpperCase())); }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid condition: " + condStr +
                        ". Valid values: GOOD, FAIR, POOR");
            }
        }

        // notes
        asset.setNotes(cols.length > 8 ? clean(cols[8]) : "");

        // typeId
        String typeIdStr = cols.length > 9 ? clean(cols[9]) : "";
        if (typeIdStr.isBlank())
            throw new IllegalArgumentException("Type ID is required");
        try {
            Long typeId = Long.parseLong(typeIdStr);
            AssetType assetType = assetTypeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "AssetType not found for ID: " + typeId));
            asset.setAssetType(assetType);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid typeId: " + typeIdStr);
        }

        // locationId (optional)
        String locationIdStr = cols.length > 10 ? clean(cols[10]) : "";
        if (!locationIdStr.isBlank()) {
            try {
                Long locationId = Long.parseLong(locationIdStr);
                locationRepository.findById(locationId).ifPresent(asset::setLocation);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid locationId: " + locationIdStr);
            }
        }

        return asset;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                    ? cell.getLocalDateTimeCellValue().toLocalDate().toString()
                    : String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCachedFormulaResultType() == CellType.NUMERIC
                    ? String.valueOf((long) cell.getNumericCellValue())
                    : cell.getRichStringCellValue().getString();
            default -> "";
        };
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK
                    && !cell.toString().isBlank()) return false;
        }
        return true;
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        try { return LocalDate.parse(value.trim(), DATE_FORMAT); }
        catch (Exception e) { return null; }
    }

    private String clean(String s) {
        return s == null ? "" : s.trim();
    }

    private BulkUploadResponseDto buildResponse(int total, int success,
                                                 List<RowErrorDto> errors) {
        BulkUploadResponseDto res = new BulkUploadResponseDto();
        res.setTotalRows(total);
        res.setSuccessCount(success);
        res.setFailedCount(errors.size());
        res.setErrors(errors);
        return res;
    }
}