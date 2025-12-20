package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.DisposalRecordService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DisposalRecordServiceImpl implements DisposalRecordService {

    private final DisposalRecordRepository disposalRepo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;

    public DisposalRecordServiceImpl(DisposalRecordRepository disposalRepo,
                                     AssetRepository assetRepo,
                                     UserRepository userRepo) {
        this.disposalRepo = disposalRepo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    public DisposalRecord createDisposal(Long assetId, DisposalRecord record) {
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        User admin = userRepo.findById(record.getApprovedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!"ADMIN".equals(admin.getRole())) {
            throw new ValidationException("Approver must be ADMIN");
        }
        if (record.getDisposalDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Disposal date cannot be in the future");
        }

        asset.setStatus("DISPOSED");
        assetRepo.save(asset);

        record.setAsset(asset);
        record.setApprovedBy(admin);
        return disposalRepo.save(record);
    }

    public DisposalRecord getDisposal(Long id) {
        return disposalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disposal record not found"));
    }

    public List<DisposalRecord> getAllDisposals() {
        return disposalRepo.findAll();
    }
}
