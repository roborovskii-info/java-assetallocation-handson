package montecarlo_simulation.app.model;

/**
 * 金融資産
 */
public class FinancialAsset {

	private AssetClass assetClass;
	private double assetAmount;

	public FinancialAsset(AssetClass assetClass, double assetAmount) {
		this.assetClass = assetClass;
		this.assetAmount = assetAmount;
	}

	public AssetClass getAssetClass() {
		return assetClass;
	}

	public double getAssetAmount() {
		return assetAmount;
	}
}