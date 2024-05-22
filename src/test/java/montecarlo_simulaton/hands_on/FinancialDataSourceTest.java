package montecarlo_simulaton.hands_on;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import montecarlo_simulation.app.FinancialDataSource;
import montecarlo_simulation.app.model.AssetClass;
import montecarlo_simulation.hands_on.MyFinancialDataSource;

public class FinancialDataSourceTest {

	FinancialDataSource testDataSource = new MyFinancialDataSource(); // new SampleGuavaFinancialDataSource();

	private double delta = 0.000001;

	@Test
	public void testRiskReturn() {

		assertEquals(0.001, testDataSource.getRisk(AssetClass.DOMESTIC_SHORTTERM), delta);
		assertEquals(0.001, testDataSource.getReturn(AssetClass.DOMESTIC_SHORTTERM), delta);

		assertEquals(0.019, testDataSource.getRisk(AssetClass.GOLD), delta);
		assertEquals(0.14, testDataSource.getReturn(AssetClass.GOLD), delta);
	}

	@Test
	public void testCorrelationMatrix() {

		assertEquals(1, testDataSource.getCorrelation(AssetClass.DOMESTIC_SHORTTERM, AssetClass.DOMESTIC_SHORTTERM),
				delta);
		assertEquals(1, testDataSource.getCorrelation(AssetClass.GOLD, AssetClass.GOLD), delta);

		assertEquals(-0.05, testDataSource.getCorrelation(AssetClass.DOMESTIC_SHORTTERM, AssetClass.GOLD), delta);
		assertEquals(0.1, testDataSource.getCorrelation(AssetClass.FOREIGN_REIT, AssetClass.GOLD), delta);
	}

}
