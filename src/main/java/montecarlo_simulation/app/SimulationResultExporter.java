package montecarlo_simulation.app;

import java.util.List;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;

/**
 * シミュレーション結果の保存
 */
public interface SimulationResultExporter {

	void exportSimulationResult(List<SimulationResult> results);
}
