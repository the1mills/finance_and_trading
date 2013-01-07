package investing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;

import trading.InvestmentResult;
import trading.Purchase;
import trading.Sale;
import visual.FreeChart;
import db4oStuff.DB4oConnect;

public class InvestingAlgorithm {

	private DB4oConnect db4oCo = null;

	private int purchaseId = 0;
	private int saleId = 0;
	private boolean buy = true;
	private boolean sell = false;
	private Vector<Double> prices = new Vector<Double>();
	private Vector<String> pricesString = null;
	private Double mostRecentPurchasePrice = null;
	private Double movingAverage = null;
	private Vector<Purchase> purchases = new Vector<Purchase>();
	private Vector<Sale> sales = new Vector<Sale>();
	private Double trajectory = 0.0;
	private Double trajectory3 = 0.0;
	private Double volatility = null;
	private Double zvalue = null;
	private boolean isPaused = false;
	// private Vector<InvestmentResult> iresults = new
	// Vector<InvestmentResult>();
	private Double currentPrice = null;
	private FreeChart fc1 = null;

	// private Double[] sellPrice = {.015,.025, .03, .04, .055, .09 };
	// private Double[] buyPriceUnderLow = { -.05,-.04,-.03,-.02, .02,.03,.04};
	// private Double[] buyPriceOver = {4.8,5.0,5.2};
	// private Double[] buyPriceUnderHigh = {-.03,-.02,-.01,0.0,.01,.02,.03,.04
	// };
	// private Double[] stopLossPrice = {.75,.8,.85 };
	// private Integer[] movingAverageValues = {11};
	// private Integer[] autoRegressionValues25 = {0,25,50,75,
	// 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 425,
	// 450, 475, 400, 425, 450, 475, 500 };
	// private Integer[] autoRegressionValues1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
	// 10,
	// 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
	// 28, 29, 30 };
	// private Double[] pValues25Low = {.25,.2,.15,.1,.05,.01};
	// private Double[] pValues25High = {.25,.2,.15,.1,.05,.01};
	// private Double[] pValues3Low = {.25,.2,.15,.1,.05,.01};
	// private Double[] pValues3High = {.25,.2,.15,.1,.05,.01};

	private JButton pauseButton = new JButton("Pause");

	private Double[] sellPrice = { .025 };
	private Double[] buyPriceUnderLow = { .11 };
	private Double[] buyPriceUnderHigh = { -.02 };

	private Double[] buyPriceOver = { 4.5 };
	private Double[] stopLossPrice = { 5.0 };
	private Integer[] movingAverageValues = { 9 };
	private Integer[] autoRegressionValues3 = { 0, 1, 2, 3, 4 };
	private Integer[] autoRegressionValues25 = { 0, 25, 50, 75, 100, 125, 150,
			175, 200, 225, 250, 275, 300, 325, 350, 375, 425, 450, 475, 400,
			425, 450, 475, 500 };
	private Integer[] autoRegressionValues1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
			27, 28, 29, 30 };

	// private Double[] pValues25Low = {.3,.25,.2,.15,.1,.05,.01,.0005,.005};
	// private Double[] pValues25High = {.3,.25,.2,.15,.1,.05,.01,.0005,.005};
	// private Double[] pValues3Low = {.3,.25,.2,.15,.1,.05,.01,.0005,.005};
	// private Double[] pValues3High = {.3,.25,.2,.15,.1,.05,.01,.0005,.005};

	private Double[] pValues25Low = { .3 };
	private Double[] pValues25High = { .3 };
	private Double[] pValues3Low = { .01 };
	private Double[] pValues3High = { .01 };
	private Double[] pValues1Low = { .01, .0005,
			.005 };
	private Double[] pValues1High = { .01, .0005,
			.005,.0001,.00001 };
//	private Double[] pValues1Low = { .3, .25, .2, .15, .1, .05, .01, .0005,
//			.005 };
//	private Double[] pValues1High = { .3, .25, .2, .15, .1, .05, .01, .0005,
//			.005 };

	public InvestingAlgorithm() {

		db4oCo = new DB4oConnect();
		java.awt.Toolkit.getDefaultToolkit().beep();
		pricesString = ReadTextFile
				.readFile("C:\\Users\\denman\\Desktop\\readInFile.txt");
		new WriteToTextFile();
		System.out.println("Number of prices in time series from text file: "
				+ pricesString.size());

		for (int i = 0; i < pricesString.size(); i++) {
			prices.add(Double.parseDouble(pricesString.get(i).trim()));
		}

		JFrame frame = new JFrame();
		frame.setContentPane(pauseButton);
		frame.pack();
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (isPaused == false) {
					isPaused = true;
					pauseButton.setForeground(Color.red);
					pauseButton.setText("Resume");
				} else {
					isPaused = false;
					pauseButton.setForeground(Color.black);
					pauseButton.setText("Pause");
				}

			}

		});
		frame.setVisible(true);
		runTheAlgorithm();
	}

	private void runTheAlgorithm() {

		int numberOfRuns = sellPrice.length * buyPriceUnderLow.length
				* buyPriceOver.length * stopLossPrice.length
				* movingAverageValues.length * pValues25Low.length
				* pValues25High.length * buyPriceUnderHigh.length
				* pValues3Low.length * pValues3High.length * pValues1Low.length
				* pValues1High.length;

		System.out.println("Number of trials: " + numberOfRuns);

		int count = 0;

		for (int y1 = 0; y1 < sellPrice.length; y1++) {
			for (int y2 = 0; y2 < buyPriceUnderLow.length; y2++) {
				for (int y3 = 0; y3 < buyPriceOver.length; y3++) {
					for (int y4 = 0; y4 < stopLossPrice.length; y4++) {
						for (int y5 = 0; y5 < movingAverageValues.length; y5++) {
							for (int y6 = 0; y6 < pValues25Low.length; y6++) {
								for (int y7 = 0; y7 < pValues25High.length; y7++) {
									for (int y8 = 0; y8 < buyPriceUnderHigh.length; y8++) {
										for (int y9 = 0; y9 < pValues3Low.length; y9++) {
											for (int y10 = 0; y10 < pValues3High.length; y10++) {
												for (int y11 = 0; y11 < pValues1Low.length; y11++) {
													for (int y12 = 0; y12 < pValues1High.length; y12++) {

														count++;

														// fc1 = new
														// FreeChart("Plot");

														// fc1.pack();
														// RefineryUtilities.centerFrameOnScreen(fc1);
														// fc1.setVisible(true);

														while (isPaused) {
															try {
																Thread.sleep(39);
															} catch (InterruptedException e) {
																// TODO
																// Auto-generated
																// catch block
																e.printStackTrace();
															}
														}

														Double totalCost = 0.0;
														Double totalRevenue = 0.0;
														Integer numberOfStopLoss = 0;
														Integer numberOfProfitableSales = 0;
														Integer numberOfPurchases = 0;
														buy = true;
														sell = false;
														purchases = new Vector<Purchase>();
														sales = new Vector<Sale>();
														Double currentPrice = null;
														Double ma = null;
														Double ar25 = null;
														Double ar1 = null;
														Double ar3 = null;

														for (int i = 0; i < prices
																.size(); i++) {

															// fc1.getSeries1().add(i,prices.get(i));

															currentPrice = prices
																	.get(i);
															ma = calculateMovingAverage(
																	i,
																	movingAverageValues[y5]);
															ar25 = calculateAutoRegression25(i);
															ar1 = calculateAutoRegression1(i);
															ar3 = calculateAutoRegression3(i);

															if (ar25 != 1.0
																	|| ar1 != 1.0) {
																int x = 0;
															}

															if (buy
																	&& (currentPrice < (ma - buyPriceUnderLow[y2]))
																	&& (currentPrice > (ma - buyPriceOver[y3]))
																	&& ((ar25 > pValues25Low[y6] || ar1 > pValues3Low[y9]) && (ar3 > pValues1Low[y11] && this.trajectory3 < 0))
																	&& this.trajectory < 0.0) {
																Date date = new Date();
																buy(currentPrice,
																		100,
																		date);
																// fc1.getSeries2().add(i,prices.get(i));
																this.mostRecentPurchasePrice = currentPrice;
																buy = false;
																sell = true;
																totalCost += 1.0;
																numberOfPurchases++;
															}

															if (buy
																	&& (currentPrice < (ma - buyPriceUnderLow[y2]))
																	&& (currentPrice > (ma - buyPriceOver[y3]))
																	&& (ar25 > pValues25High[y7] || ar1 > pValues3High[y10]
																			&& (ar3 > pValues1High[y12] && this.trajectory3 > 0))
																	&& this.trajectory > 0.0) {

																Date date = new Date();
																buy(currentPrice,
																		100,
																		date);
																// fc1.getSeries2().add(i,prices.get(i));
																this.mostRecentPurchasePrice = currentPrice;
																buy = false;
																sell = true;
																totalCost += 1.0;
																numberOfPurchases++;
															}

															if (buy
																	&& (currentPrice < (ma - buyPriceUnderLow[y2]))
																	&& (currentPrice > (ma - buyPriceOver[y3]))
																	&& (ar25 < pValues25High[y7] || ar1 < pValues3High[y10]
																			&& (ar3 < pValues1High[y12] && this.trajectory3 > 0))
																	&& this.trajectory >= 0.0) {

																Date date = new Date();
																buy(currentPrice,
																		100,
																		date);
																// fc1.getSeries2().add(i,prices.get(i));
																this.mostRecentPurchasePrice = currentPrice;
																buy = false;
																sell = true;
																totalCost += 1.0;
																numberOfPurchases++;
															}

															if (sell
																	&& currentPrice > (mostRecentPurchasePrice + sellPrice[y1])) {

																Date date = new Date();
																sell(currentPrice,
																		100,
																		date,
																		false);
																sell = false;
																buy = true;
																// fc1.getSeries3().add(i,prices.get(i));
																totalCost += 1.0;
																numberOfProfitableSales++;
															}
															if (sell
																	&& currentPrice < (mostRecentPurchasePrice - stopLossPrice[y4])) {

																Date date = new Date();
																sell(currentPrice,
																		100,
																		date,
																		true);
																sell = false;
																buy = true;
																// fc1.getSeries3().add(i,prices.get(i));
																totalCost += 1.0;
																numberOfStopLoss++;
															}
														}

														if (purchases.size() > sales
																.size()) {
															purchases
																	.remove(purchases
																			.size() - 1);
														}

														for (int i = 0; i < purchases
																.size(); i++) {
															totalCost += purchases
																	.get(i)
																	.getPricePurchasedAt()
																	* purchases
																			.get(i)
																			.getQuantityPurchased();
														}

														for (int i = 0; i < sales
																.size(); i++) {
															totalRevenue += sales
																	.get(i)
																	.getPriceSoldAt()
																	* sales.get(
																			i)
																			.getQuantitySold();
														}

														Double profit = totalRevenue
																- totalCost;
														Double sellPrice = this.sellPrice[y1];
														Double buyPriceUnderLow = this.buyPriceUnderLow[y2];
														Double buyPriceOver = this.buyPriceOver[y3];
														Double stopLossPrice = this.stopLossPrice[y4];
														Integer movingAverageValues = this.movingAverageValues[y5];
														Double pValue25Low = this.pValues25Low[y6];
														Double pValue25High = this.pValues25High[y7];
														Double pValue1Low = this.pValues3Low[y9];
														Double pValue1High = this.pValues3High[y10];
														Double buyPriceUnderHigh = this.buyPriceUnderHigh[y8];
														Integer numberSales = sales
																.size();
														Integer numberPurchases = purchases
																.size();

														if (count % 1 == 0) {
															System.out
																	.println("Profit: "
																			+ profit
																			+ " , "
																			+ count
																			+ "/"
																			+ numberOfRuns
																			+ "PvalueLow: "
																			+ pValue25Low
																			+ "PvalueHigh: "
																			+ pValue25High);

														}

														if (profit > -1000) {

															InvestmentResult ir = new InvestmentResult(
																	profit,
																	sellPrice,
																	buyPriceUnderLow,
																	buyPriceOver,
																	stopLossPrice,
																	movingAverageValues,
																	numberPurchases,
																	numberSales,
																	numberOfPurchases,
																	numberOfProfitableSales,
																	numberOfStopLoss,
																	pValue25Low,
																	pValue25High,
																	buyPriceUnderHigh,
																	pValue1Low,
																	pValue1High);
															
															db4oCo.insertIntoDatabase(ir);

															WriteToTextFile
																	.writeToTextFile("Profit: "
																			+ profit
																			+ ", SellPrice: "
																			+ sellPrice
																			+ ", BuyPriceUnderLow: "
																			+ buyPriceUnderLow
																			+ ", BuyPriceOver: "
																			+ buyPriceOver
																			+ ", StopLossPrice: "
																			+ stopLossPrice
																			+ ", "
																			+ "MovingAverageValues: "
																			+ movingAverageValues
																			+ ", No. Purchases: "
																			+ numberPurchases
																			+ ", No. of Sales"
																			+ numberSales
																			+ ", No. of Purchases Check: "
																			+ numberOfPurchases
																			+ ", No. of Profitable Sales: "
																			+ numberOfProfitableSales
																			+ ", No. of Stop Loss: "
																			+ numberOfStopLoss
																			+ ", PValue25Low: "
																			+ pValue25Low
																			+ ", PValue25High: "
																			+ pValue25High
																			+ ", buyPriceUnderHigh: "
																			+ buyPriceUnderHigh

																			+ ", PValue1Low: "
																			+ pValue1Low
																			+ ", PValue1High: "
																			+ pValue1High);

														}
													}
												}
											}

										}
									}
								}
							}

						}
					}
				}
			}
		}
		// fc1.pack();
		// RefineryUtilities.centerFrameOnScreen(fc1);
		// fc1.setVisible(true);
		InvestmentResult ir = new InvestmentResult(30.0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		db4oCo.openJTableWithObject(ir);
		db4oCo.openJTableWithClass(InvestmentResult.class);
		WriteToTextFile.closeFile();
	}

	private Double calculateAutoRegression25(int x) {
		Double pValue = 1.0;
		Vector<Double> diffValues = new Vector<Double>();
		Double mean = null;

		if (x > this.autoRegressionValues25[this.autoRegressionValues25.length - 1]) {

			for (int i = autoRegressionValues25.length - 1; i > 0; i--) {

				Integer goBackFarther = autoRegressionValues25[i];
				Integer goBackNearer = autoRegressionValues25[i - 1];
				Double priceFar = prices.get(x - goBackFarther);
				Double priceNear = prices.get(x - goBackNearer);

				diffValues.add(priceNear - priceFar);

			}

			mean = returnMean(diffValues);
			this.trajectory = mean;
			this.volatility = getStandardDev(diffValues, mean);
			this.zvalue = (mean - 0.0)
					/ (this.volatility / Math.sqrt(diffValues.size()));
			pValue = Gaussian.Phi(this.zvalue);

			if (mean >= 0) {
				return 1 - pValue;
			}
		}

		return pValue;
	}

	private Double calculateAutoRegression1(int x) {
		Double pValue = 1.0;
		Vector<Double> diffValues = new Vector<Double>();
		Double mean = null;

		if (x > this.autoRegressionValues1[this.autoRegressionValues1.length - 1]) {

			for (int i = autoRegressionValues1.length - 1; i > 0; i--) {

				Integer goBackFarther = autoRegressionValues1[i];
				Integer goBackNearer = autoRegressionValues1[i - 1];
				Double priceFar = prices.get(x - goBackFarther);
				Double priceNear = prices.get(x - goBackNearer);

				diffValues.add(priceNear - priceFar);

			}

			mean = returnMean(diffValues);
			this.trajectory = mean;
			this.volatility = getStandardDev(diffValues, mean);
			this.zvalue = (mean - 0.0)
					/ (this.volatility / Math.sqrt(diffValues.size()));
			pValue = Gaussian.Phi(this.zvalue);

			if (mean >= 0) {
				return 1 - pValue;
			}
		}

		return pValue;
	}

	private Double calculateAutoRegression3(int x) {
		Double pValue = 1.0;
		Vector<Double> diffValues = new Vector<Double>();
		Double mean = null;

		if (x > this.autoRegressionValues3[this.autoRegressionValues3.length - 1]) {

			for (int i = autoRegressionValues3.length - 1; i > 0; i--) {

				Integer goBackFarther = autoRegressionValues3[i];
				Integer goBackNearer = autoRegressionValues3[i - 1];
				Double priceFar = prices.get(x - goBackFarther);
				Double priceNear = prices.get(x - goBackNearer);

				diffValues.add(priceNear - priceFar);

			}

			mean = returnMean(diffValues);
			this.trajectory3 = mean;
			this.volatility = getStandardDev(diffValues, mean);
			this.zvalue = (mean - 0.0)
					/ (this.volatility / Math.sqrt(diffValues.size()));
			pValue = Gaussian.Phi(this.zvalue);

			if (mean >= 0) {
				return 1 - pValue;
			}
		}

		return pValue;
	}

	private Double getStandardDev(Vector<Double> diffValues, Double mean) {

		Double sum = 0.0;

		for (int i = 0; i < diffValues.size(); i++) {
			sum += (diffValues.get(i) - mean) * (diffValues.get(i) - mean);
		}

		return Math.sqrt((sum / (diffValues.size() - 1)));
	}

	private Double returnMean(Vector<Double> diffValues) {

		Integer total = diffValues.size();
		Double sum = 0.0;

		for (int i = 0; i < diffValues.size(); i++) {
			sum += diffValues.get(i);
		}
		return sum / total;
	}

	public static void main(String[] args) {

		new InvestingAlgorithm();
	}

	private Double calculateMovingAverage(int position, int back) {

		Integer i = null;
		Integer total = 0;
		Double sum = 0.0;

		for (i = position; i > Math.max(position - back, 0); i--) {
			sum += prices.get(i - 1);
			total++;
		}

		return movingAverage = sum / total;

	}

	public void buy(Double price, Integer quantity, Date date) {

		purchases.add(new Purchase(this.purchaseId, price, quantity, date));
		this.purchaseId++;
	}

	private void sell(Double price, Integer quantity, Date date,
			boolean stopLoss) {

		Purchase aPurchase = null;
		Sale aSale = new Sale(this.saleId, price, quantity, date, stopLoss);
		this.saleId++;
		sales.add(aSale);
		for (int i = 0; i < purchases.size(); i++) {
			if (!purchases.get(i).isSold()) {
				aPurchase = purchases.get(i);
				aPurchase.setSold(true);
			}
		}
	}

}
