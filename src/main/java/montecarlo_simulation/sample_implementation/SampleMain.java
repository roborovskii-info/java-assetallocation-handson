package montecarlo_simulation.sample_implementation;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import montecarlo_simulation.app.FinancialDataSource;
import montecarlo_simulation.app.MonteCarloSimulation;
import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.RandomGenerator;
import montecarlo_simulation.app.SimulationResultExporter;
import montecarlo_simulation.app.SimulationResultPrinter;
import montecarlo_simulation.app.chart.Chart;
import montecarlo_simulation.app.model.AssetClass;
import montecarlo_simulation.app.model.FinancialAsset;
import montecarlo_simulation.app.model.Portfolio;

public class SampleMain {
	public static void main(String[] args) {
		// 金融資産の初期化
		List<FinancialAsset> assetList = initAssetList();

		// リスクリターンと相関行列のデータ
		FinancialDataSource dataSource = new SampleHashMapFinancialDataSource();

		// ポートフォリオ
		Portfolio portfolio = new Portfolio(assetList, dataSource);

		// シミュレーション回数
		int numberOfSimulations = 100;
		// シミュレーション年数
		int numberOfYears = 20;
		// 乱数生成クラス
		RandomGenerator randomGenerator = new SampleRandomGenerator();
		// シミュレーション実行クラス
		MonteCarloSimulation simulation = new SampleMonteCarloSimulation(randomGenerator);
		// シミュレーションの実行
		List<SimulationResult> results = simulation.runSimulation(portfolio, numberOfSimulations, numberOfYears);

		// 結果を出力
		SimulationResultPrinter printer = new SampleSimulationResultPrinter();
		printer.print(portfolio, results);

		// 結果をチャートで表示
		Chart lineChart = new Chart();
		lineChart.show(portfolio, results);

		// 結果をファイルに保存
		SimulationResultExporter exporter = new SampleSimulationResultExporter(new File("export"));
		exporter.exportSimulationResult(results);
	}

	// アセットクラスと、初期の資産残高を設定
	public static List<FinancialAsset> initAssetList() {
		FinancialAsset domesticShortTerm = new FinancialAsset(AssetClass.DOMESTIC_SHORTTERM, 0);
		FinancialAsset domesticStock = new FinancialAsset(AssetClass.DOMESTIC_STOCK, 1000);
		FinancialAsset domesticBond = new FinancialAsset(AssetClass.DOMESTIC_BOND, 1000);
		FinancialAsset domesticREIT = new FinancialAsset(AssetClass.DOMESTIC_REIT, 0);
		FinancialAsset foreignShortTerm = new FinancialAsset(AssetClass.FOREIGN_SHORTTERM, 1000);
		FinancialAsset foreignStock = new FinancialAsset(AssetClass.FOREIGN_STOCK, 1000);
		FinancialAsset foreignBond = new FinancialAsset(AssetClass.FOREIGN_BOND, 1000);
		FinancialAsset foreignREIT = new FinancialAsset(AssetClass.FOREIGN_REIT, 0);
		FinancialAsset gold = new FinancialAsset(AssetClass.GOLD, 0);

		List<FinancialAsset> assetList = Arrays.asList(domesticShortTerm, domesticBond, domesticStock, domesticREIT,
				foreignShortTerm, foreignBond, foreignStock, foreignREIT, gold);
		return assetList;
	}

}
