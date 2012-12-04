package trading;

public class InvestmentResult {

	private Double profit = null;
	private Double sellPrice = null;
	private Double buyPriceUnderLow = null;
	private Double BuyPriceOver = null;
	private Double StopLossPrice = null;
	private Integer MovingAverageValues = null;
	private Integer numberPurchases = null;
	private Integer numberSales = null;
	private Integer numberPurchasesCheck = null;
	private Integer numberProfitableSales = null;
	private Integer numberStopLoss = null;
	private Double PValue25Low = null;
	private Double PValue25High = null;
	private Double buyPriceUnderHigh = null;
	private Double PValue1Low = null;
	private Double PValue1High = null;

	public InvestmentResult(Double profit, Double sellPrice,
			Double buyPriceUnderLow, Double buyPriceOver, Double stopLossPrice,
			Integer movingAverageValues, Integer numberPurchases,
			Integer numberSales, Integer numberPurchasesCheck,
			Integer numberProfitableSales, Integer numberStopLoss,
			Double pValue25Low, Double pValue25High, Double buyPriceUnderHigh,
			Double pValue1Low, Double pValue1High) {
		super();
		this.profit = profit;
		this.sellPrice = sellPrice;
		this.buyPriceUnderLow = buyPriceUnderLow;
		BuyPriceOver = buyPriceOver;
		StopLossPrice = stopLossPrice;
		MovingAverageValues = movingAverageValues;
		this.numberPurchases = numberPurchases;
		this.numberSales = numberSales;
		this.numberPurchasesCheck = numberPurchasesCheck;
		this.numberProfitableSales = numberProfitableSales;
		this.numberStopLoss = numberStopLoss;
		PValue25Low = pValue25Low;
		PValue25High = pValue25High;
		this.buyPriceUnderHigh = buyPriceUnderHigh;
		PValue1Low = pValue1Low;
		PValue1High = pValue1High;
	}

	
	public Double getProfit() {
		return profit;
	}
	
//	public Double getProfit() {
//		return profit;
//	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Double getBuyPriceUnderLow() {
		return buyPriceUnderLow;
	}

	public void setBuyPriceUnderLow(Double buyPriceUnderLow) {
		this.buyPriceUnderLow = buyPriceUnderLow;
	}

	public Double getBuyPriceOver() {
		return BuyPriceOver;
	}

	public void setBuyPriceOver(Double buyPriceOver) {
		BuyPriceOver = buyPriceOver;
	}

	public Double getStopLossPrice() {
		return StopLossPrice;
	}

	public void setStopLossPrice(Double stopLossPrice) {
		StopLossPrice = stopLossPrice;
	}

	public Integer getMovingAverageValues() {
		return MovingAverageValues;
	}

	public void setMovingAverageValues(Integer movingAverageValues) {
		MovingAverageValues = movingAverageValues;
	}

	public Integer getNumberPurchases() {
		return numberPurchases;
	}

	public void setNumberPurchases(Integer numberPurchases) {
		this.numberPurchases = numberPurchases;
	}

	public Integer getNumberSales() {
		return numberSales;
	}

	public void setNumberSales(Integer numberSales) {
		this.numberSales = numberSales;
	}

	public Integer getNumberPurchasesCheck() {
		return numberPurchasesCheck;
	}

	public void setNumberPurchasesCheck(Integer numberPurchasesCheck) {
		this.numberPurchasesCheck = numberPurchasesCheck;
	}

	public Integer getNumberProfitableSales() {
		return numberProfitableSales;
	}

	public void setNumberProfitableSales(Integer numberProfitableSales) {
		this.numberProfitableSales = numberProfitableSales;
	}

	public Integer getNumberStopLoss() {
		return numberStopLoss;
	}

	public void setNumberStopLoss(Integer numberStopLoss) {
		this.numberStopLoss = numberStopLoss;
	}

	public Double getPValue25Low() {
		return PValue25Low;
	}

	public void setPValue25Low(Double pValue25Low) {
		PValue25Low = pValue25Low;
	}

	public Double getPValue25High() {
		return PValue25High;
	}

	public void setPValue25High(Double pValue25High) {
		PValue25High = pValue25High;
	}

	public Double getBuyPriceUnderHigh() {
		return buyPriceUnderHigh;
	}

	public void setBuyPriceUnderHigh(Double buyPriceUnderHigh) {
		this.buyPriceUnderHigh = buyPriceUnderHigh;
	}

	public Double getPValue1Low() {
		return PValue1Low;
	}

	public void setPValue1Low(Double pValue1Low) {
		PValue1Low = pValue1Low;
	}

	public Double getPValue1High() {
		return PValue1High;
	}

	public void setPValue1High(Double pValue1High) {
		PValue1High = pValue1High;
	}

}
