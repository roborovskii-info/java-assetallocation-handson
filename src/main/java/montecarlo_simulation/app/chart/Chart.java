package montecarlo_simulation.app.chart;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.model.FinancialAsset;
import montecarlo_simulation.app.model.Portfolio;

/**
 * シミュレーション結果チャート
 */
public class Chart {

	/**
	 * シミュレーション結果を表示する
	 * 
	 * @param portfolio
	 * @param results
	 */
	public void show(Portfolio portfolio, List<SimulationResult> results) {
		// 線グラフを作成
		JFreeChart chart = createLineChart(results);

		JPanel piePanel = new JPanel();
		piePanel.setLayout(new BorderLayout());
		ChartPanel pieChartPanel = new ChartPanel(createPieChart(portfolio));
		piePanel.add(pieChartPanel, BorderLayout.CENTER);
		// リスクとリターンのラベルを作成
		JPanel labelPanel = createLabelPanel(portfolio);
		piePanel.add(labelPanel, BorderLayout.SOUTH);

		// UI表示
		JFrame frame = new JFrame("Portfolio Simulation");
		frame.setSize(1200, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// チャートを含むパネルを作成
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ChartPanel(chart), piePanel);
		splitPane.setDividerLocation(800); // 分割位置を設定
		frame.setContentPane(splitPane);
		// 表示
		SwingUtilities.invokeLater(() -> {
			frame.setVisible(true);
		});
	}

	private JFreeChart createLineChart(List<SimulationResult> results) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		results.stream().forEach(r -> {
			double[] amount = r.getPrices().stream().mapToDouble(Double::doubleValue).toArray();
			double[][] seriesData = new double[2][amount.length];// X軸とY軸のデータを格納する配列
			// X軸のデータ(年数)を設定
			seriesData[0] = IntStream.range(0, amount.length).mapToDouble(e -> e).toArray();
			// Y軸のデータ(資産額)を設定
			seriesData[1] = amount;
			dataset.addSeries(r.getNo(), seriesData);
		});
		JFreeChart lineChart = ChartFactory.createXYLineChart(null, "year", "amount", dataset, PlotOrientation.VERTICAL,
				false, true, false);

		return lineChart;
	}

	private JPanel createLabelPanel(Portfolio portfolio) {
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 2));
		DecimalFormat format = new DecimalFormat("0.00");
		JLabel returnLabel = new JLabel("リターン: " + format.format(portfolio.calculatePortfolioReturn() * 100) + "%");
		JLabel riskLabel = new JLabel("リスク:" + format.format(portfolio.calculatePortfolioRisk() * 100) + "%");
		labelPanel.add(returnLabel);
		labelPanel.add(riskLabel);
		return labelPanel;
	}

	private JFreeChart createPieChart(Portfolio portfolio) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (FinancialAsset asset : portfolio.getFinancialAssets()) {
			if (asset.getAssetAmount() > 0) {
				dataset.setValue(asset.getAssetClass().getAssetClassName(), asset.getAssetAmount());
			}
		}
		JFreeChart chart = ChartFactory.createPieChart("Portfolio", dataset, false, false, false);
		return chart;
	}
}
