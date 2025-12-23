package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.DisposalRecord;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.DisposalRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DisposalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisposalRecordServiceImpl implements DisposalRecordService {

    private final DisposalRecordRepository disposalRepo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;  

    public DisposalRecordServiceImpl(
            DisposalRecordRepository disposalRepo,
            AssetRepository assetRepo,
            UserRepository userRepo) {       
        this.disposalRepo = disposalRepo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;            
    }

    @Override
    public DisposalRecord createDisposal(Long assetId, DisposalRecord disposal) {

       
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

    
        User approver = userRepo.findById(
                disposal.getApprovedBy().getId()
        ).orElseThrow(() -> new ResourceNotFoundException("Approver not found"));

    
        disposal.setAsset(asset);
        disposal.setApprovedBy(approver);

       
        return disposalRepo.save(disposal);
    }

    @Override
    public DisposalRecord getDisposal(Long id) {
        return disposalRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Disposal record not found"));
    }

    @Override
    public List<DisposalRecord> getAllDisposals() {
        return disposalRepo.findAll();
    }
}
