package montecarlo_simulation;

import java.util.Arrays;
import java.util.Random;

/**
 * 簡易的なポートフォリオの将来推移シミュレーション
 */
public class SimulationSample {

	public static void main(String[] args) {
		// 各金融資産のリターン
		double[] returns = { 0.1, 0.03, 0.08 }; // 国内株式、国内債券、国内REITのリターン
		// 各金融資産のリスク（標準偏差）
		double[] risks = { 0.15, 0.04, 0.18 }; // 国内株式、国内債券、国内REITのリスク
		// 各金融資産の金額
		double[] amount = { 1000, 1000, 1000 }; // 債券、株式、REITの金額
		// 資産合計
		double totalAmount = Arrays.stream(amount).sum();
		// 相関行列
		double[][] correlationMatrix = { { 1.0, 0.2, 0.6 }, { 0.2, 1.0, 0.3 }, { 0.6, 0.3, 1.0 } };

		// ポートフォリオのリターンを計算
		double portfolioReturn = 0.0;
		for (int i = 0; i < returns.length; i++) {
			double weight = amount[i] / totalAmount;
			portfolioReturn += weight * returns[i];
		}

		// ポートフォリオのリスクを計算
		double portfolioVariance = 0.0;
		for (int i = 0; i < risks.length; i++) {
			for (int j = 0; j < risks.length; j++) {
				double weightA = amount[i] / totalAmount;
				double weightB = amount[j] / totalAmount;
				portfolioVariance += weightA * weightB * risks[i] * risks[j] * correlationMatrix[i][j];
			}
		}
		double portfolioRisk = Math.sqrt(portfolioVariance);

		// モンテカルロシミュレーションの設定
		int numSimulations = 10000;// シミュレーション回数
		int numYears = 20; // シミュレーション年数

		double[] finalValues = new double[numSimulations];
		Random random = new Random();

		for (int sim = 0; sim < numSimulations; sim++) {
			double portfolioValue = totalAmount;
			for (int year = 0; year < numYears; year++) {
				double randomReturn = portfolioReturn + random.nextGaussian() * portfolioRisk;
				portfolioValue *= (1 + randomReturn);
			}
			finalValues[sim] = portfolioValue;
		}

		// シミュレーション結果の統計情報を表示
		double sum = 0.0;
		for (double value : finalValues) {
			sum += value;
		}
		double mean = sum / numSimulations;
		System.out.printf("初期資産額: %.0f円\n", totalAmount);
		System.out.printf("ポートフォリオのリターン: %.2f%%\n", portfolioReturn * 100);
		System.out.printf("ポートフォリオのリスク: %.2f%%\n", portfolioRisk * 100);
		System.out.printf("20年後の平均資産価値: %.0f円\n", mean);
	}
}
