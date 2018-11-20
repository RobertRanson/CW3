package auctionhouse;

import java.util.ArrayList;
import java.util.List;

public class Lot {
	String SellerName;
	int number;
	String description;
	Money reservePrice;
	LotStatus lotStatus;
	
	List<Buyer> noteInterestList = new ArrayList<Buyer>();
	
	public Lot(String SellerName, int number, String description, Money reserverPrice) {
		this.SellerName = SellerName;
		this.number = number;
		this.description = description;
		this.reservePrice = reserverPrice;
		this.lotStatus = LotStatus.UNSOLD;
		
	}
	//Note interest in lot
	public void noteInterest(Buyer aBuyer) {
		this.noteInterestList.add(aBuyer);
	}

	public void setLotStatus(LotStatus lotStatus) {
		this.lotStatus = lotStatus;
	}

	public LotStatus getLotStatus() {
		return lotStatus;
	}

	public String getSellerName() {
		return SellerName;
	}

	public void setSellerName(String sellerName) {
		this.SellerName = sellerName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Money getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(Money reservePrice) {
		this.reservePrice = reservePrice;
	}
	
	

}
