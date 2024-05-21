package montecarlo_simulation.app;

import java.util.List;

import montecarlo_simulation.app.model.Portfolio;

/**
 * モンテカルロシミュレーション
 */
public interface MonteCarloSimulation {

	/**
	 * シミュレーション結果
	 */
	public static class SimulationResult {
		private int no;
		private List<Double> prices;// 資産推移

		public SimulationResult(int no, List<Double> prices) {
			this.prices = prices;
			this.no = no;
		}

		public int getNo() {
			return no;
		}

		public List<Double> getPrices() {
			return prices;
		}
	}

	/**
	 * モンテカルロシミュレーションを実行する
	 * 
	 * @param portfolio
	 * @param numberOfSimulations シミュレーション実行回数
	 * @param numberOfYears       シミュレーション年数
	 * @return
	 */
	public List<SimulationResult> runSimulation(Portfolio portfolio, int numberOfSimulations, int numberOfYears);
}