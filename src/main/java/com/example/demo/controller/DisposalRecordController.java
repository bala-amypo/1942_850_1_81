package com.example.demo.controller;

import com.example.demo.entity.DisposalRecord;
import com.example.demo.service.DisposalRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disposals")
@Tag(name = "Disposals")
public class DisposalRecordController {

    private final DisposalRecordService disposalRecordService;

    public DisposalRecordController(DisposalRecordService disposalRecordService) {
        this.disposalRecordService = disposalRecordService;
    }

    // POST /api/disposals/{assetId}
    @PostMapping("/{assetId}")
    public DisposalRecord createDisposal(@PathVariable Long assetId,
                                         @RequestBody DisposalRecord record) {
        return disposalRecordService.createDisposal(assetId, record);
    }

    // GET /api/disposals/{id}
    @GetMapping("/{id}")
    public DisposalRecord getDisposal(@PathVariable Long id) {
        return disposalRecordService.getDisposal(id);
    }
}
