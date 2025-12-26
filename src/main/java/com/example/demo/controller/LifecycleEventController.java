package com.example.demo.controller;

import com.example.demo.entity.LifecycleEvent;
import com.example.demo.service.LifecycleEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Lifecycle Events")
public class LifecycleEventController {

    private final LifecycleEventService lifecycleEventService;

    public LifecycleEventController(LifecycleEventService lifecycleEventService) {
        this.lifecycleEventService = lifecycleEventService;
    }

    // POST /api/events/{assetId}/{userId}
    @PostMapping("/{assetId}/{userId}")
    public LifecycleEvent logEvent(@PathVariable Long assetId,
                                   @PathVariable Long userId,
                                   @RequestBody LifecycleEvent event) {
        return lifecycleEventService.logEvent(assetId, userId, event);
    }

    // GET /api/events/asset/{assetId}
    @GetMapping("/asset/{assetId}")
    public List<LifecycleEvent> getEventsForAsset(@PathVariable Long assetId) {
        return lifecycleEventService.getEventsForAsset(assetId);
    }
}
