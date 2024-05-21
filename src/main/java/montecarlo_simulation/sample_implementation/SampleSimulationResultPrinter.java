package montecarlo_simulation.sample_implementation;

import java.util.List;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.SimulationResultPrinter;
import montecarlo_simulation.app.model.Portfolio;

public class SampleSimulationResultPrinter implements SimulationResultPrinter {

	@Override
	public void print(Portfolio portfolio, List<SimulationResult> results) {
		System.out.printf("シミュレーション回数: %s回\n", results.size());
		System.out.printf("初期資産額: %.0f円\n", portfolio.calcTotalAssetAmount());
		System.out.printf("ポートフォリオのリターン: %.2f%%\n", portfolio.calculatePortfolioReturn() * 100);
		System.out.printf("ポートフォリオのリスク: %.2f%%\n", portfolio.calculatePortfolioRisk() * 100);

		// 最終年度の資産額の最大値
		double maxAsset = results.stream().map(result -> result.getPrices()).map(list -> list.get(list.size() - 1))
				.max(Double::compare).get();
		System.out.printf("最終年度の資産額の最大値: %.0f円\n", maxAsset);

		// 初年度資産の平均額
		double averageAsset = results.stream().map(result -> result.getPrices()).mapToDouble(list -> list.get(1))
				.average().getAsDouble();
		System.out.printf("初年度資産の平均額: %.0f円\n", averageAsset);

		// 最終年度の資産が、初期資産額の２倍以上になったシミュレーションの数
		double threshold = portfolio.calcTotalAssetAmount() * 2;
		long count = results.stream().map(result -> result.getPrices())
				.filter(list -> list.get(list.size() - 1) >= threshold).count();
		System.out.printf("初期資産額の２倍以上になったシミュレーションの数: %s\n", count);
	}

}
