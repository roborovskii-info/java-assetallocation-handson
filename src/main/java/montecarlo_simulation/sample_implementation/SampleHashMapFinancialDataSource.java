package montecarlo_simulation.sample_implementation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.io.CharStreams;

import montecarlo_simulation.app.FinancialDataSource;
import montecarlo_simulation.app.model.AssetClass;

/**
 * 金融データソース(HashMap版)
 */
public class SampleHashMapFinancialDataSource implements FinancialDataSource {

	private Map<AssetClass, Double> riskMap = new HashMap<>();
	private Map<AssetClass, Double> returnMap = new HashMap<>();
	private Map<AssetClass, Map<AssetClass, Double>> correlationMap = new HashMap<>();

	public SampleHashMapFinancialDataSource() {
		loadRiskReturn();
		loadCorrelationMatrix();
	}

	@Override
	public double getRisk(AssetClass assetClass) {
		return riskMap.get(assetClass);
	}

	@Override
	public double getReturn(AssetClass assetClass) {
		return returnMap.get(assetClass);
	}

	@Override
	public double getCorrelation(AssetClass a, AssetClass b) {
		return correlationMap.get(a).get(b);
	}

	private void loadRiskReturn() {
		try (InputStream is = getClass().getResourceAsStream("/risk-return.csv")) {
			List<String> lines = CharStreams.readLines(new InputStreamReader(is, "UTF-8"));
			// ヘッダー行を飛ばして、各行を読み込む
			lines.stream().skip(1).forEach(line -> {
				String[] items = line.split(",");
				AssetClass assetClass = AssetClass.valueOf(items[0]);
				double returnValue = Double.parseDouble(items[1]);
				double riskValue = Double.parseDouble(items[2]);
				riskMap.put(assetClass, riskValue);
				returnMap.put(assetClass, returnValue);
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 相関行列を読み込む
	 * 
	 * @return 相関行列
	 */
	private void loadCorrelationMatrix() {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/correlation.csv")))) {
			List<String> lines = reader.lines().collect(Collectors.toList());
			// ヘッダー行の読み込み
			List<AssetClass> headers = Arrays.asList(lines.get(0).split(",")).stream().skip(1)
					.map(s -> AssetClass.valueOf(s)).collect(Collectors.toList());
			// ヘッダー行を飛ばして、各行を読み込む
			lines.stream().skip(1).forEach(line -> {
				String[] items = line.split(",");
				AssetClass a = AssetClass.valueOf(items[0]);
				for (int i = 1; i < items.length; i++) {
					AssetClass b = headers.get(i - 1);
					double correlation = Double.parseDouble(items[i]);
					correlationMap.computeIfAbsent(a, k -> new HashMap<>()).put(b, correlation);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
