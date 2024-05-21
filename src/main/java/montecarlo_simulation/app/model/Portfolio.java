package montecarlo_simulation.app.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import montecarlo_simulation.app.FinancialDataSource;

/**
 * ポートフォリオ
 */
public class Portfolio {
	private List<FinancialAsset> financialAssets;
	private FinancialDataSource financialDataSource;

	public Portfolio(List<FinancialAsset> financialAssets, FinancialDataSource financialDataSource) {
		this.financialAssets = financialAssets;
		this.financialDataSource = financialDataSource;
	}

	/**
	 * ポートフォリオのリターンを計算する
	 * 
	 * <pre>
	 * 各資産のリターンの加重平均を計算する
	 * </pre>
	 * 
	 * @return ポートフォリオのリターン
	 */
	public double calculatePortfolioReturn() {
		double totalAsset = calcTotalAssetAmount();
		double portfolioReturn = 0.0;
		for (FinancialAsset asset : financialAssets) {
			double weight = asset.getAssetAmount() / totalAsset;
			double returnValue = financialDataSource.getReturn(asset.getAssetClass());
			portfolioReturn = portfolioReturn + weight * returnValue;
		}
		return portfolioReturn;
	}

	/**
	 * ポートフォリオのリスクを計算する
	 * 
	 * @return
	 */
	public double calculatePortfolioRisk() {
		double totalAsset = calcTotalAssetAmount();

		Map<AssetClass, FinancialAsset> mapPerAssetClass = financialAssets.stream()
				.collect(Collectors.toMap(e -> e.getAssetClass(), e -> e));
		List<AssetClass> assetClassList = mapPerAssetClass.keySet().stream().collect(Collectors.toList());

		double portfolioRisk = 0.0;
		for (AssetClass assetClassA : assetClassList) {
			FinancialAsset assetA = mapPerAssetClass.get(assetClassA);
			for (AssetClass assetClassB : assetClassList) {
				FinancialAsset assetB = mapPerAssetClass.get(assetClassB);
				double correlation = financialDataSource.getCorrelation(assetClassA, assetClassB);
				double weightA = assetA.getAssetAmount() / totalAsset;
				double weightB = assetB.getAssetAmount() / totalAsset;
				double riskA = financialDataSource.getRisk(assetA.getAssetClass());
				double riskB = financialDataSource.getRisk(assetB.getAssetClass());
				portfolioRisk = portfolioRisk + weightA * riskA * weightB * riskB * correlation;
			}
		}
		return Math.sqrt(portfolioRisk);
	}

	/**
	 * 資産の合計金額を計算する
	 * 
	 * @return 資産の合計金額
	 */
	public double calcTotalAssetAmount() {
		return financialAssets.stream().mapToDouble(f -> f.getAssetAmount()).sum();
	}

	public List<FinancialAsset> getFinancialAssets() {
		return financialAssets;
	}
}