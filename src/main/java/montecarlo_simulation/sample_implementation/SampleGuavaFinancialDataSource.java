package montecarlo_simulation.sample_implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.io.CharStreams;

import montecarlo_simulation.app.FinancialDataSource;
import montecarlo_simulation.app.model.AssetClass;

/**
 * 金融データソース(Guava版)
 */
public class SampleGuavaFinancialDataSource implements FinancialDataSource {

	/**
	 * リスク・リターンテーブル
	 */
	private Table<AssetClass, RiskReturnColumn, Double> riskReturnTable;
	/**
	 * 相関係数テーブル
	 */
	private Table<AssetClass, AssetClass, Double> correlationTable;

	public static enum RiskReturnColumn {
		RISK, RETURN
	}

	public SampleGuavaFinancialDataSource() {
		loadRiskReturn();
		loadCorrelationMatrix();
	}

	private void loadRiskReturn() {
		// データをテーブルに読み込む
		riskReturnTable = HashBasedTable.create();
		try (InputStream is = getClass().getResourceAsStream("/risk-return.csv")) {
			List<String> lines = CharStreams.readLines(new InputStreamReader(is, "UTF-8"));
			String[] headers = null;
			for (String line : lines) {
				String[] values = line.split(",");
				if (headers == null) {
					// ヘッダーを読み込む
					headers = values;
				} else {
					// データ行を読み込む
					AssetClass rowHeader = AssetClass.valueOf(values[0]);
					for (int i = 1; i < values.length; i++) {
						RiskReturnColumn colHeader = RiskReturnColumn.valueOf(headers[i]);
						riskReturnTable.put(rowHeader, colHeader, Double.parseDouble(values[i]));
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void loadCorrelationMatrix() {
		correlationTable = HashBasedTable.create();
		try (InputStream is = getClass().getResourceAsStream("/correlation.csv")) {
			List<String> lines = CharStreams.readLines(new InputStreamReader(is, "UTF-8"));
			String[] headers = null;
			for (String line : lines) {
				String[] values = line.split(",");
				if (headers == null) {
					// ヘッダーを読み込む
					headers = values;
				} else {
					// データ行を読み込む
					AssetClass rowHeader = AssetClass.valueOf(values[0]);
					for (int i = 1; i < values.length; i++) {
						AssetClass colHeader = AssetClass.valueOf(headers[i]);
						correlationTable.put(rowHeader, colHeader, Double.parseDouble(values[i]));
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public double getRisk(AssetClass assetClass) {
		return riskReturnTable.get(assetClass, RiskReturnColumn.RISK);
	}

	@Override
	public double getReturn(AssetClass assetClass) {
		return riskReturnTable.get(assetClass, RiskReturnColumn.RETURN);
	}

	@Override
	public double getCorrelation(AssetClass a, AssetClass b) {
		return correlationTable.get(a, b);
	}
}
