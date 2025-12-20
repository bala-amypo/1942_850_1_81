@Service
public class DisposalRecordServiceImpl implements DisposalRecordService {

    private final DisposalRecordRepository repo;
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;

    public DisposalRecordServiceImpl(
            DisposalRecordRepository repo,
            AssetRepository assetRepo,
            UserRepository userRepo) {
        this.repo = repo;
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
    }

    public DisposalRecord createDisposal(Long assetId, DisposalRecord record) {
        record.setAsset(assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found")));
        record.setApprovedBy(userRepo.findById(record.getApprovedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        record.getAsset().setStatus("DISPOSED");
        assetRepo.save(record.getAsset());

        return repo.save(record);
    }

    public DisposalRecord getDisposal(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disposal record not found"));
    }

    public List<DisposalRecord> getAllDisposals() {
        return repo.findAll();
    }
}
