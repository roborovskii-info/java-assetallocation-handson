package montecarlo_simulation.sample_implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import montecarlo_simulation.app.MonteCarloSimulation;
import montecarlo_simulation.app.RandomGenerator;
import montecarlo_simulation.app.model.Portfolio;

/**
 * モンテカルロシミュレーション
 */
public class SampleMonteCarloSimulation implements MonteCarloSimulation {

	private RandomGenerator randomGenerator;

	public SampleMonteCarloSimulation(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	@Override
	public List<SimulationResult> runSimulation(Portfolio portfolio, int numberOfSimulations, int numberOfYears) {
		List<SimulationResult> results = new ArrayList<>();
		double portfolioReturn = portfolio.calculatePortfolioReturn();
		double portfolioRisk = portfolio.calculatePortfolioRisk();

		IntStream.range(0, numberOfSimulations).forEach(n -> {
			List<Double> portfolioValues = new ArrayList<>();
			double assetAmount = portfolio.calcTotalAssetAmount();
			portfolioValues.add(assetAmount);
			// 0年からnumberOfYears年までのシミュレーションを実行
			for (int j = 0; j < numberOfYears; j++) {
				double randomValue = randomGenerator.generate();
				double randomReturnValue = portfolioRisk * randomValue;
				// 前年の資産額にリターンをかける
				assetAmount = assetAmount * (1 + portfolioReturn + randomReturnValue);
				if (assetAmount < 0) {
					assetAmount = 0;
				}
				portfolioValues.add(assetAmount);
			}
			results.add(new SimulationResult(n, portfolioValues));
		});
		return results;
	}
}