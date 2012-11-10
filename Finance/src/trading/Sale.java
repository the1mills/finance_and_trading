package trading;

import java.util.Date;

public class Sale {
	
	private Integer saleId = null;
	private Integer quantitySold = null;
	private Double priceSoldAt = null;
	private Date dateSold = null;
	private boolean stopLoss = false;

	public Sale(int saleId, Double price, Integer quantity, Date date, boolean stopLoss) {
		
		this.saleId = saleId;
		this.priceSoldAt= price;
		this.quantitySold = quantity;
		this.dateSold = date;
		this.stopLoss = stopLoss;
	}

	
	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public Integer getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(Integer quantitySold) {
		this.quantitySold = quantitySold;
	}

	public Double getPriceSoldAt() {
		return priceSoldAt;
	}

	public void setPriceSoldAt(Double priceSoldAt) {
		this.priceSoldAt = priceSoldAt;
	}

	public Date getDateSold() {
		return dateSold;
	}

	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold;
	}

	public boolean isStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(boolean stopLoss) {
		this.stopLoss = stopLoss;
	}
}
