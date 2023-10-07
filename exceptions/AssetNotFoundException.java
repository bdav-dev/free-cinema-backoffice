package exceptions;

public class AssetNotFoundException extends RuntimeException {
    
    public AssetNotFoundException(String asset) {
        super(asset + " not found.");
    }

}
