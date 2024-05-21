package montecarlo_simulation.hands_on;

import java.util.Random;

import montecarlo_simulation.app.RandomGenerator;

public class MyRandomGenerator implements RandomGenerator {

	private Random random = new Random();

	@Override
	public double generate() {
		return random.nextGaussian();
	}
}
