package montecarlo_simulation.hands_on;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import montecarlo_simulation.app.FinancialDataSource;
import montecarlo_simulation.app.model.AssetClass;

public class MyFinancialDataSource implements FinancialDataSource {

	/**
	 * リスク・リターンテーブル
	 */
	private Table<AssetClass, RiskReturnColumn, Double> riskReturnTable = HashBasedTable.create();
	/**
	 * 相関係数テーブル
	 */
	private Table<AssetClass, AssetClass, Double> correlationTable = HashBasedTable.create();

	public static enum RiskReturnColumn {
		RISK, RETURN
	}

	public MyFinancialDataSource() {
		loadRiskReturn();
		loadCorrelationMatrix();
	}

	// クラスパス上の risk-return.csv から、リスクとリターンの情報を読み込んでriskReturnTableに格納する
	// テストケースは FinancialDataSourceTest に実装済み
	private void loadRiskReturn() {
		// 実装
	}

	// クラスパス上の correlation.csv から、相関係数を読み込んでcorrelationTableに格納する
	// テストケースは FinancialDataSourceTest に実装済み
	private void loadCorrelationMatrix() {
		// 実装

	}

	/**
	 * アセットクラスのリスクを取得する
	 */

	@Override
	public double getRisk(AssetClass assetClass) {
		return riskReturnTable.get(assetClass, RiskReturnColumn.RISK);
	}

	/**
	 * アセットクラスのリターンを取得する
	 */

	@Override
	public double getReturn(AssetClass assetClass) {
		return riskReturnTable.get(assetClass, RiskReturnColumn.RETURN);
	}

	/**
	 * アセットクラス間の相関係数を取得する
	 */

	@Override
	public double getCorrelation(AssetClass a, AssetClass b) {
		return correlationTable.get(a, b);
	}
}
