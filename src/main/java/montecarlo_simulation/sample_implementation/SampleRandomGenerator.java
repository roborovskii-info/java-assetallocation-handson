package montecarlo_simulation.sample_implementation;

import java.util.Random;

import montecarlo_simulation.app.RandomGenerator;

public class SampleRandomGenerator implements RandomGenerator {

	// シード固定の乱数生成器
	private Random random = new Random(1L);

	@Override
	public double generate() {
		return random.nextGaussian();
	}
}
