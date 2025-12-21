package com.example.demo.service;

import com.example.demo.entity.TransferRecord;
import java.util.List;

public interface TransferRecordService {

    TransferRecord createTransfer(Long assetId, TransferRecord record);

    TransferRecord getTransfer(Long id);  

    List<TransferRecord> getTransfersForAsset(Long assetId);
}
