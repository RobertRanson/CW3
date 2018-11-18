package auctionhouse;

public class Lot {
	String SellerName;
	int number;
	String description;
	Money reservePrice;
	
	public Lot(String SellerName, int number, String description, Money reserverPrice) {
		this.SellerName = SellerName;
		this.number = number;
		this.description = description;
		this.reservePrice = reserverPrice;
		
	}
	
	

}
