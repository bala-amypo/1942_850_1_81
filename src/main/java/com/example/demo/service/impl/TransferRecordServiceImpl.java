@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository repo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;

    public TransferRecordServiceImpl(
            TransferRecordRepository repo,
            AssetRepository assetRepo,
            UserRepository userRepo) {
        this.repo = repo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    public TransferRecord createTransfer(Long assetId, TransferRecord record) {
        record.setAsset(assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found")));
        record.setApprovedBy(userRepo.findById(record.getApprovedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return repo.save(record);
    }

    public List<TransferRecord> getTransfersForAsset(Long assetId) {
        return repo.findByAssetId(assetId);
    }

    public TransferRecord getTransfer(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found"));
    }
}
