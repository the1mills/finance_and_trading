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
	
	//ibm @ 300
	private Double[] sellPrice = {.06,.07,.08,.09,.1,.11,.13};
	private Double[] buyPriceLow = {.05,.06,.07,.08,.09,.1,.11,.12,.13,.14,.15};
	private Double[] buyPriceHigh = {1.4,1.8,2.0,2.05,2.11,2.12};
	private Double[] stopLossPrice = {.13,.14,.15,.16,.17,.18,.19,.2,.3,.4,.5};
	private Integer[] movingAverageValues = {3,4,5,7};
	
	
	
	
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
		
		for(int y1 = 0; y1 < sellPrice.length; y1++){
			for(int y2 = 0; y2 < buyPriceLow.length; y2++){
				for(int y3 = 0; y3 < buyPriceHigh.length; y3++){
					for(int y4 = 0; y4 < stopLossPrice.length; y4++){
						for(int y5 = 0; y5 < movingAverageValues.length; y5++){
							
							Double totalCost = 0.0;
							Double totalRevenue = 0.0;
							Integer numberOfStopLoss = 0;
							Integer numberOfProfitableSales = 0;
							Integer numberOfPurchases = 0;
							buy = true;
							sell = false;
							purchases = new Vector<Purchase>();
							sales = new Vector<Sale>();
							
							
							
		for(int i = 0; i < prices.size(); i++){
			
			Double currentPrice = prices.get(i);
			Double ma = calculateMovingAverage(Math.min(i+1, prices.size()-1),y5);
			
			if(buy && currentPrice < (ma - buyPriceLow[y2]) && (currentPrice > (ma - buyPriceHigh[y3]))){
				
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
		
		Double profit = (totalRevenue - totalCost);
		Double sellPrice = this.sellPrice[y1];
		Double buyPriceLow = this.buyPriceLow[y2];
		Double buyPriceHigh = this.buyPriceHigh[y3];
		Double stopLossPrice = this.stopLossPrice[y4];
		Integer movingAverageValues = this.movingAverageValues[y5];
		Integer salesSize = sales.size();
		Integer purchasesSize = purchases.size();
		
		System.out.println("Profit: " + profit);
		WriteToTextFile.writeToTextFile("Profit: " + profit + ", SellPrice: " + sellPrice + ", BuyPriceLow: " + 
		buyPriceLow + ", BuyPriceHigh: " + buyPriceHigh +", StopLossPrice: " + stopLossPrice + ", " +
				"MovingAverageValues: " + movingAverageValues + ", No. Purchases: " + purchasesSize + ", No. of Sales" 
				+  salesSize + ", No. of Purchases Check: " +  numberOfPurchases + ", No. of Profitable Sales: " + numberOfProfitableSales + ", No. of Stop Loss: " + numberOfStopLoss);
		
						}
					}
				}
				
			}
		}
		
		WriteToTextFile.closeFile();
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
		Vector<Purchase> purchaseVector = new Vector<Purchase>();
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
