package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.TransferRecord;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.TransferRecordRepository;
import com.example.demo.service.TransferRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository transferRecordRepository;
    private final AssetRepository assetRepository;

    public TransferRecordServiceImpl(
            TransferRecordRepository transferRecordRepository,
            AssetRepository assetRepository) {

        this.transferRecordRepository = transferRecordRepository;
        this.assetRepository = assetRepository;
    }

    public TransferRecord createTransfer(Long assetId, TransferRecord record) {
        Asset asset = assetRepository.findById(assetId).orElse(null);
        record.setAsset(asset);
        return transferRecordRepository.save(record);
    }

    public List<TransferRecord> getTransfersForAsset(Long assetId) {
        return transferRecordRepository.findByAssetId(assetId);
    }
}
