package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.TransferRecord;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.TransferRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TransferRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository transferRepo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;

    public TransferRecordServiceImpl(
            TransferRecordRepository transferRepo,
            AssetRepository assetRepo,
            UserRepository userRepo) {

        this.transferRepo = transferRepo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    @Override
    public TransferRecord createTransfer(Long assetId, TransferRecord record) {

        if (record.getTransferDate() != null &&
                record.getTransferDate().after(new Date())) {
            throw new ValidationException("Transfer date cannot be in the future");
        }

        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Asset not found"));

        User approver = userRepo.findById(
                record.getApprovedBy().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        if (!"ADMIN".equalsIgnoreCase(approver.getRole())) {
            throw new ValidationException("Only admin can approve transfer");
        }

        asset.setCurrentHolder(approver);
        asset.setStatus("ASSIGNED");
        assetRepo.save(asset);

        record.setAsset(asset);
        record.setApprovedBy(approver);

        return transferRepo.save(record);
    }

    @Override
    public List<TransferRecord> getTransfersForAsset(Long assetId) {
        return transferRepo.findByAsset_Id(assetId);
    }

    @Override
    public TransferRecord getTransfer(Long id) {
        return transferRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transfer record not found"));
    }
}
