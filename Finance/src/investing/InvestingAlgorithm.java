package investing;

import java.util.Date;
import java.util.Vector;

import trading.Purchase;
import trading.Sale;

public class InvestingAlgorithm {

	private int purchaseId = 0;
	private int saleId = 0;
	private boolean buy = true;
	private boolean sell = false;
	private  Vector<Double> prices = new Vector<Double>();
	private Vector<String> pricesString = null;
	private  Double mostRecentPurchasePrice = null;
	private  Double movingAverage = null;
	private  Vector<Purchase> purchases = new Vector<Purchase>();
	private  Vector<Sale> sales = new Vector<Sale>();
	private Double trajectory = 0.0;
	private Double volatility = null;
	private Double zvalue = null;
	
	//ibm @ 300
//	private Double[] sellPrice = {.06,.07,.08,.09,.1,.11,.13,.45};
	private Double[] sellPrice = {.06,.07,.08,.09,.1,.11};
//	private Double[] buyPriceLow = {.05,.06,.07,.08,.09,.1,.11,.12,.13,.14,.15,.9};
	private Double[] buyPriceLow = {.08,.09,.1,.11,.12,.13,.14};
	private Double[] buyPriceHigh = {1.4,1.8,2.0,2.05,2.12};
	private Double[] stopLossPrice = {.15,.16,.17,.18,.19,.2,.3};
//	private Double[] stopLossPrice = {.13,.14,.15,.16,.17,.18,.19,.2,.3,.4,.5};
//	private Integer[] movingAverageValues = {1,2,3,4,5,6};
	private Integer[] movingAverageValues = {1,2,3,4};
	private Integer[] autoRegressionValues = {3,6,12,25,50,100,200,300,400,500};
//	private Double[] pValues = {.001,.001,.0001,.0002,.003,.0004,.0005};
	private Double[] pValues = {.001,.0007,.0004,.0002};
	
	
	public InvestingAlgorithm() {
		
		
		pricesString = ReadTextFile.readFile("C:\\Users\\denman\\Desktop\\readInFile.txt");
		new WriteToTextFile();
		System.out.println("Number of prices in time series from text file: " + pricesString.size());
	
		
		for(int i = 0; i < pricesString.size(); i++){
			prices.add(Double.parseDouble(pricesString.get(i).trim()));
		}
		
		runTheAlgorithm();
	}
	
	private void runTheAlgorithm() {
		
		int numberOfRuns = sellPrice.length*buyPriceLow.length*buyPriceHigh.length*movingAverageValues.length*
				autoRegressionValues.length*pValues.length;
		int count = 0;
		
		for(int y1 = 0; y1 < sellPrice.length; y1++){
			for(int y2 = 0; y2 < buyPriceLow.length; y2++){
				for(int y3 = 0; y3 < buyPriceHigh.length; y3++){
					for(int y4 = 0; y4 < stopLossPrice.length; y4++){
						for(int y5 = 0; y5 < movingAverageValues.length; y5++){
							for(int y6 = 0; y6 < pValues.length; y6++){
								
								count++;
							
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
							Double ar = null;
							
		for(int i = 0; i < prices.size(); i++){
			
			currentPrice = prices.get(i);
			ma = calculateMovingAverage(Math.min(i+1, prices.size()-1),movingAverageValues[y5]);
			ar = calculateAutoRegression(i);
			
			
			if(buy && (currentPrice < (ma - buyPriceLow[y2])) && (currentPrice > (ma - buyPriceHigh[y3]))
					&& ar > pValues[y6] && this.trajectory <= 0.0){
				
				Date date = new Date();
				buy(currentPrice,10,date);
				this.mostRecentPurchasePrice = currentPrice;
				buy = false;
				sell = true;
				totalCost+=1.0;
				numberOfPurchases++;
			}
			
			if(buy && (currentPrice < (ma - buyPriceLow[y2])) && (currentPrice > (ma - buyPriceHigh[y3]))
					&& ar > pValues[y6] && this.trajectory >= 0.0){
				
				Date date = new Date();
				buy(currentPrice,10,date);
				this.mostRecentPurchasePrice = currentPrice;
				buy = false;
				sell = true;
				totalCost+=1.0;
				numberOfPurchases++;
			}
			
			if(buy && (currentPrice < (ma - buyPriceLow[y2])) && (currentPrice > (ma - buyPriceHigh[y3]))
					&& ar < pValues[y6] && this.trajectory >= 0.0){
				
				Date date = new Date();
				buy(currentPrice,10,date);
				this.mostRecentPurchasePrice = currentPrice;
				buy = false;
				sell = true;
				totalCost+=1.0;
				numberOfPurchases++;
			}
			
			if(sell && currentPrice > (mostRecentPurchasePrice + sellPrice[y1])){
				
				Date date = new Date();
				sell(currentPrice,10,date,false);
				sell = false;
				buy = true;
				totalCost+=1.0;
				numberOfProfitableSales++;
			}
			if(sell && currentPrice < (mostRecentPurchasePrice - stopLossPrice[y4])){
				
				Date date = new Date();
				sell(currentPrice,10,date,true);
				sell = false;
				buy = true;
				totalCost+=1.0;
				numberOfStopLoss++;
			}
		}
		
		if(purchases.size() > sales.size()){
			purchases.remove(purchases.size()-1);
		}
		
		for(int i = 0; i < purchases.size(); i++){
			totalCost += purchases.get(i).getPricePurchasedAt()*purchases.get(i).getQuantityPurchased();
		}
		
		for(int i = 0; i < sales.size(); i++){
			totalRevenue += sales.get(i).getPriceSoldAt()*sales.get(i).getQuantitySold();
		}
		
		Double profit = totalRevenue - totalCost;
		Double sellPrice = this.sellPrice[y1];
		Double buyPriceLow = this.buyPriceLow[y2];
		Double buyPriceHigh = this.buyPriceHigh[y3];
		Double stopLossPrice = this.stopLossPrice[y4];
		Integer movingAverageValues = this.movingAverageValues[y5];
		Double  pValue = this.pValues[y6];
		Integer salesSize = sales.size();
		Integer purchasesSize = purchases.size();
		
		System.out.println("Profit: " + profit + " , " + count + "/" + numberOfRuns);
		WriteToTextFile.writeToTextFile("Profit: " + profit + ", SellPrice: " + sellPrice + ", BuyPriceLow: " + 
		buyPriceLow + ", BuyPriceHigh: " + buyPriceHigh +", StopLossPrice: " + stopLossPrice + ", " +
				"MovingAverageValues: " + movingAverageValues + ", No. Purchases: " + purchasesSize + ", No. of Sales" 
				+  salesSize + ", No. of Purchases Check: " +  numberOfPurchases + ", No. of Profitable Sales: " + numberOfProfitableSales 
				+ ", No. of Stop Loss: " + numberOfStopLoss
				+ ", PValue: " + pValue);
		
							}
						}
					}
				
				}
			}
		}
		
		WriteToTextFile.closeFile();
	}

	private Double calculateAutoRegression(int x) {
		Double pValue = 1.0;
		Vector<Double> diffValues = new Vector<Double>();
		Double mean = null;
		
		if(x > this.autoRegressionValues[this.autoRegressionValues.length-1]){
			
			for(int i = autoRegressionValues.length-1; i > 0; i--){
				
				Integer goBackFarther = autoRegressionValues[i];
				Integer goBackNearer = autoRegressionValues[i-1];
				Double priceFar = prices.get(x - goBackFarther);
				Double priceNear = prices.get(x - goBackNearer);
				
				diffValues.add(priceNear - priceFar);
				
			}
			
			mean = returnMean(diffValues);
			this.trajectory = mean;
			this.volatility =  getStandardDev(diffValues, mean);
			this.zvalue = (mean - 0.0)/(this.volatility/Math.sqrt(diffValues.size()));
			pValue = Gaussian.Phi(this.zvalue);
		
			if(mean >=0){
				return 1-pValue;
			}
		}
	
		return pValue;
	}

	private Double getStandardDev(Vector<Double> diffValues, Double mean) {
	
		Double sd = null;
		Double sum = 0.0;
		
		for(int i = 0; i < diffValues.size(); i++){
			sum += (diffValues.get(i)- mean)*(diffValues.get(i)- mean);
		}
		
		return Math.sqrt((sum/(diffValues.size()-1)));
	}

	private Double returnMean(Vector<Double> diffValues) {
		Double mean = null;
		Integer total = diffValues.size();
		Double sum = 0.0;
		
		for(int i = 0; i < diffValues.size(); i++){
			sum+=diffValues.get(i);
		}
		return mean = sum/total;
	}

	public static void main(String[] args){
		
		new InvestingAlgorithm();
	}

	private Double calculateMovingAverage(int x, int y){
		
		Integer i = null;
		Integer total = Math.min(x,y);
		Double sum = 0.0;
		
		for(i = x; i > x - total; i--){
			sum += prices.get(i);
		}
		
		return movingAverage = sum/total;
		
	}
	
	
	public void buy(Double price, Integer quantity, Date date){
		
		purchases.add(new Purchase(this.purchaseId,price,quantity,date));
		this.purchaseId++;
	}
	
	private void sell(Double price, Integer quantity, Date date, boolean stopLoss){
		
		Purchase aPurchase = null;
		Sale aSale = new Sale(this.saleId,price,quantity,date, stopLoss);
		this.saleId++;
		sales.add(aSale);
		for(int i = 0; i < purchases.size(); i++){
			if(!purchases.get(i).isSold()){
				aPurchase = purchases.get(i);
				aPurchase.setSold(true);
			}
		}
	}
	
	
}
