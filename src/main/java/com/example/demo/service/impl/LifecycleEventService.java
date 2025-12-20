@Service
public class LifecycleEventServiceImpl implements LifecycleEventService {

    private final LifecycleEventRepository eventRepo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;

    public LifecycleEventServiceImpl(
            LifecycleEventRepository eventRepo,
            AssetRepository assetRepo,
            UserRepository userRepo) {
        this.eventRepo = eventRepo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    public LifecycleEvent logEvent(Long assetId, Long userId, LifecycleEvent event) {
        event.setAsset(assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found")));
        event.setPerformedBy(userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return eventRepo.save(event);
    }

    public List<LifecycleEvent> getEventsForAsset(Long assetId) {
        return eventRepo.findByAssetId(assetId);
    }

    public LifecycleEvent getEvent(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }
}
