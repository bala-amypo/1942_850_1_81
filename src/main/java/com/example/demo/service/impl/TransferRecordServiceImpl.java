package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.TransferRecordService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository transferRepo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;

    public TransferRecordServiceImpl(TransferRecordRepository transferRepo,
                                     AssetRepository assetRepo,
                                     UserRepository userRepo) {
        this.transferRepo = transferRepo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    public TransferRecord createTransfer(Long assetId, TransferRecord record) {
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        User admin = userRepo.findById(record.getApprovedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!"ADMIN".equals(admin.getRole())) {
            throw new ValidationException("Approver must be ADMIN");
        }
        if (record.getFromDepartment().equals(record.getToDepartment())) {
            throw new ValidationException("Departments must differ");
        }
        if (record.getTransferDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Transfer date cannot be in the future");
        }

        record.setAsset(asset);
        record.setApprovedBy(admin);
        return transferRepo.save(record);
    }

    public List<TransferRecord> getTransfersForAsset(Long assetId) {
        return transferRepo.findByAssetId(assetId);
    }

    public TransferRecord getTransfer(Long id) {
        return transferRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found"));
    }
}
