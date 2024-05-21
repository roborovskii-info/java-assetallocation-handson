package montecarlo_simulation.hands_on;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import montecarlo_simulation.app.MonteCarloSimulation;
import montecarlo_simulation.app.RandomGenerator;
import montecarlo_simulation.app.model.Portfolio;

public class MyMonteCarloSimulation implements MonteCarloSimulation {

	private RandomGenerator randomGenerator;

	public MyMonteCarloSimulation(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	/**
	 * モンテカルロシミュレーションを実行する
	 */
	@Override
	public List<SimulationResult> runSimulation(Portfolio portfolio, int numberOfSimulations, int numberOfYears) {
		double portfolioReturn = portfolio.calculatePortfolioReturn();
		double portfolioRisk = portfolio.calculatePortfolioRisk();

		List<SimulationResult> results = new ArrayList<>();
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
