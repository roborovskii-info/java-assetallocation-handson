package montecarlo_simulation.app.model;

/**
 * アセットクラス定義
 */
public enum AssetClass {
	DOMESTIC_SHORTTERM("短期金融資産"), //
	DOMESTIC_STOCK("国内株式"), //
	DOMESTIC_BOND("国内債券"), //
	DOMESTIC_REIT("国内REIT"), //
	FOREIGN_SHORTTERM("外国短期金融資産"), //
	FOREIGN_STOCK("外国株式"), //
	FOREIGN_BOND("外国債券"), //
	FOREIGN_REIT("外国REIT"), //
	GOLD("金");

	private String assetClassName;

	private AssetClass(String name) {
		this.assetClassName = name;
	}

	public String getAssetClassName() {
		return assetClassName;
	}
}