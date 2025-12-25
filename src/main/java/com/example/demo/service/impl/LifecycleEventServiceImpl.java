package com.example.demo.service.impl;

import com.example.demo.entity.Asset;
import com.example.demo.entity.LifecycleEvent;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LifecycleEventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LifecycleEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifecycleEventServiceImpl implements LifecycleEventService {

    private final LifecycleEventRepository lifecycleRepo;
        private final AssetRepository assetRepo;
            private final UserRepository userRepo;

                public LifecycleEventServiceImpl(
                            LifecycleEventRepository lifecycleRepo,
                                        AssetRepository assetRepo,
                                                    UserRepository userRepo) {

                                                            this.lifecycleRepo = lifecycleRepo;
                                                                    this.assetRepo = assetRepo;
                                                                            this.userRepo = userRepo;
                                                                                }

                                                                                    @Override
                                                                                        public LifecycleEvent logEvent(Long assetId, Long userId, LifecycleEvent event) {

                                                                                                Asset asset = assetRepo.findById(assetId)
                                                                                                                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

                                                                                                                        User user = userRepo.findById(userId)
                                                                                                                                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                                                                                                                                                if (event.getEventType() == null || event.getEventType().isEmpty()) {
                                                                                                                                                            throw new ValidationException("Event type is required");
                                                                                                                                                                    }

                                                                                                                                                                            event.setAsset(asset);
                                                                                                                                                                                    event.setPerformedBy(user);

                                                                                                                                                                                            return lifecycleRepo.save(event);
                                                                                                                                                                                                }

                                                                                                                                                                                                    @Override
                                                                                                                                                                                                        public List<LifecycleEvent> getEventsForAsset(Long assetId) {
                                                                                                                                                                                                                return lifecycleRepo.findByAssetId(assetId);
                                                                                                                                                                                                                    }

                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                            public LifecycleEvent getEvent(Long id) {
                                                                                                                                                                                                                                    return lifecycleRepo.findById(id)
                                                                                                                                                                                                                                                    .orElseThrow(() ->
                                                                                                                                                                                                                                                                            new ResourceNotFoundException("Lifecycle event not found"));
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                