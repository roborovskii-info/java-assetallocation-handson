package montecarlo_simulation.app;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.chart.Chart;
import montecarlo_simulation.app.model.AssetClass;
import montecarlo_simulation.app.model.FinancialAsset;
import montecarlo_simulation.app.model.Portfolio;
import montecarlo_simulation.sample_implementation.SampleHashMapFinancialDataSource;
import montecarlo_simulation.sample_implementation.SampleMonteCarloSimulation;
import montecarlo_simulation.sample_implementation.SampleRandomGenerator;
import montecarlo_simulation.sample_implementation.SampleSimulationResultExporter;

public class Main {
	public static void main(String[] args) {
		// 金融資産の初期化
		// アセットクラスと、初期の資産残高を設定
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

		// リスクリターンと相関行列のデータ
		FinancialDataSource dataSource = new SampleHashMapFinancialDataSource();
		// ポートフォリオ
		Portfolio portfolio = new Portfolio(assetList, dataSource);

		System.out.printf("初期資産額: %.0f円\n", portfolio.calcTotalAssetAmount());
		System.out.printf("ポートフォリオのリターン: %.2f%%\n", portfolio.calculatePortfolioReturn() * 100);
		System.out.printf("ポートフォリオのリスク: %.2f%%\n", portfolio.calculatePortfolioRisk() * 100);

		int numberOfSimulations = 100;
		int numberOfYears = 20;
		// 乱数生成
		RandomGenerator randomGenerator = new SampleRandomGenerator();
		// シミュレーション実行クラス
		SampleMonteCarloSimulation simulation = new SampleMonteCarloSimulation(randomGenerator);
		// シミュレーション実行
		List<SimulationResult> results = simulation.runSimulation(portfolio, numberOfSimulations, numberOfYears);

		Chart lineChart = new Chart();
		lineChart.show(portfolio, results);

		SimulationResultExporter exporter = new SampleSimulationResultExporter(new File("export"));
		exporter.exportSimulationResult(results);
	}
}
