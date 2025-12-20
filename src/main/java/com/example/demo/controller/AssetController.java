@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    @PostMapping
    public Asset create(@RequestBody Asset asset) {
        return service.createAsset(asset);
    }

    @GetMapping
    public List<Asset> getAll() {
        return service.getAllAssets();
    }

    @GetMapping("/{id}")
    public Asset get(@PathVariable Long id) {
        return service.getAsset(id);
    }
}
