package montecarlo_simulation.app;

import java.util.List;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.model.Portfolio;

/**
 * シミュレーション結果の表示
 */
public interface SimulationResultPrinter {

	void print(Portfolio portfolio, List<SimulationResult> results);
}
