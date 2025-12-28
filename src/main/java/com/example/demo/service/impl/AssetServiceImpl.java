package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AssetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public AssetServiceImpl(AssetRepository assetRepository,
                            UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Asset createAsset(Asset asset) {

        if (asset.getCurrentHolder() != null &&
            asset.getCurrentHolder().getId() != null) {

            User user = userRepository.findById(
                    asset.getCurrentHolder().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with id " +
                            asset.getCurrentHolder().getId()
                    )
            );

            asset.setCurrentHolder(user);
        }

        return assetRepository.save(asset);
    }

    @Override
    public Asset getAsset(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Asset not found"));
    }

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    @Override
    public Asset updateStatus(Long id, String status) {
        Asset asset = getAsset(id);
        asset.setStatus(status);
        return assetRepository.save(asset);
    }
}
