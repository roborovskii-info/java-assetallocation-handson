package montecarlo_simulation.hands_on;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.SimulationResultExporter;

public class MySimulationResultExporter implements SimulationResultExporter {

	private File rootDir;

	public MySimulationResultExporter(File rootDir) {
		this.rootDir = rootDir;
		rootDir.mkdirs();
	}

	@Override
	public void exportSimulationResult(List<SimulationResult> results) {
		long exportStartTime = System.currentTimeMillis();

		// 1シミュレーションごとにファイルを作成して出力する
		// 並列処理を行って処理速度を上げる
		for (SimulationResult result : results) {
			File file = new File(rootDir, UUID.randomUUID().toString() + ".txt");
			file.deleteOnExit();
			try {
				Files.write(file.toPath(), result.getPrices().toString().getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		System.out.println("export time:" + (System.currentTimeMillis() - exportStartTime));
	}

}
