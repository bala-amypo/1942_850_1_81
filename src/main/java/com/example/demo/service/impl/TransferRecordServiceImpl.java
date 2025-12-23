package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.TransferRecord;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.TransferRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TransferRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository transferRepo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;   // ✅ ADD

    public TransferRecordServiceImpl(
            TransferRecordRepository transferRepo,
            AssetRepository assetRepo,
            UserRepository userRepo) {        // ✅ ADD
        this.transferRepo = transferRepo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    @Override
    public TransferRecord createTransfer(Long assetId, TransferRecord record) {

       
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        User newHolder = userRepo.findById(
                record.getToUser().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        asset.setCurrentHolder(newHolder);
        asset.setStatus("ASSIGNED");  
        assetRepo.save(asset);

        // 4️⃣ Save transfer record
        record.setAsset(asset);
        return transferRepo.save(record);
    }

    @Override
    public List<TransferRecord> getTransfersForAsset(Long assetId) {
        return transferRepo.findByAssetId(assetId);
    }

    @Override
    public TransferRecord getTransfer(Long id) {
        return transferRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transfer record not found"));
    }
}
