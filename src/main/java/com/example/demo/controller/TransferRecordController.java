package com.example.demo.controller;

import com.example.demo.entity.TransferRecord;
import com.example.demo.service.TransferRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@Tag(name = "Transfers")
public class TransferRecordController {

    private final TransferRecordService transferRecordService;

    public TransferRecordController(TransferRecordService transferRecordService) {
        this.transferRecordService = transferRecordService;
    }

    // POST /api/transfers/{assetId}
    @PostMapping("/{assetId}")
    public TransferRecord createTransfer(@PathVariable Long assetId,
                                         @RequestBody TransferRecord record) {
        return transferRecordService.createTransfer(assetId, record);
    }

    // GET /api/transfers/asset/{assetId}
    @GetMapping("/asset/{assetId}")
    public List<TransferRecord> getTransfersForAsset(@PathVariable Long assetId) {
        return transferRecordService.getTransfersForAsset(assetId);
    }
}
