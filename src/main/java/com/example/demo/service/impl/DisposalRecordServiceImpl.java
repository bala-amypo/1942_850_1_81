package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.DisposalRecord;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.DisposalRecordRepository;
import com.example.demo.service.DisposalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisposalRecordServiceImpl implements DisposalRecordService {

    private final DisposalRecordRepository disposalRecordRepository;
    private final AssetRepository assetRepository;

    public DisposalRecordServiceImpl(
            DisposalRecordRepository disposalRecordRepository,
            AssetRepository assetRepository) {

        this.disposalRecordRepository = disposalRecordRepository;
        this.assetRepository = assetRepository;
    }

    public DisposalRecord createDisposal(Long assetId, DisposalRecord disposal) {
        Asset asset = assetRepository.findById(assetId).orElse(null);
        disposal.setAsset(asset);
        return disposalRecordRepository.save(disposal);
    }

    public List<DisposalRecord> getAllDisposals() {
        return disposalRecordRepository.findAll();
    }
}
