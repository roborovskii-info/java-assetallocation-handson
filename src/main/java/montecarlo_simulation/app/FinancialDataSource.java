package montecarlo_simulation.app;

import montecarlo_simulation.app.model.AssetClass;

public interface FinancialDataSource {

	/**
	 * アセットクラスのリスクを取得する
	 * 
	 * @param assetClass
	 * @return
	 */
	double getRisk(AssetClass assetClass);

	/**
	 * アセットクラスのリターンを取得する
	 * 
	 * @param assetClass
	 * @return
	 */
	double getReturn(AssetClass assetClass);

	/**
	 * アセットクラス間の相関係数を取得する
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	double getCorrelation(AssetClass a, AssetClass b);
}
