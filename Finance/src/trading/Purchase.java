package trading;

import java.util.Date;

public class Purchase {

	private Integer purchaseId = null;
	private Integer quantityPurchased = null;
	private Double pricePurchasedAt = null;
	private Date datePurchased = null;
	private boolean sold = false;
	
	public Purchase(int id, Double price, Integer quantity, Date date) {
		this.purchaseId = id;
		this.pricePurchasedAt = price;
		this.quantityPurchased = quantity;
		this.datePurchased = date;
	}
	
	
	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Integer getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(Integer quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public Double getPricePurchasedAt() {
		return pricePurchasedAt;
	}

	public void setPricePurchasedAt(Double pricePurchasedAt) {
		this.pricePurchasedAt = pricePurchasedAt;
	}

	public Date getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(Date datePurchased) {
		this.datePurchased = datePurchased;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}
	

}
