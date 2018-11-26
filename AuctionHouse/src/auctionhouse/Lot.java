package auctionhouse;

import java.util.ArrayList;
import java.util.List;

public class Lot {
	String SellerName;
	int number;
	String description;
	Money reservePrice;
	LotStatus lotStatus;
	Money currentBid;
	String currentBuyerName;
	String auctioneerName;
	String auctioneerAddress;
	
	
	List<Buyer> noteInterestList = new ArrayList<Buyer>();
	
	
	
	public Lot(String SellerName, int number, String description, Money reserverPrice) {
		this.SellerName = SellerName;
		this.number = number;
		this.description = description;
		this.reservePrice = reserverPrice;
		this.lotStatus = LotStatus.UNSOLD;
		this.currentBid = new Money("0.00");
		this.currentBuyerName = null;
			
		
	}
	//Note interest in lot
	public void noteInterest(Buyer aBuyer) {
		this.noteInterestList.add(aBuyer);
	}
	public List<Buyer> getNoteInterestList() {
		return noteInterestList;
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


	public Money getCurrentBid() {
		return currentBid;
	}
	public void setCurrentBid(Money currentBid) {
		this.currentBid = currentBid;
	}
	public String getCurrentBuyerName() {
		return currentBuyerName;
	}
	public void setCurrentBuyerName(String currentBuyerName) {
		this.currentBuyerName = currentBuyerName;
	}
	public String getAuctioneerName() {
		return auctioneerName;
	}
	public void setAuctioneerName(String auctioneerName) {
		this.auctioneerName = auctioneerName;
	}
	public String getAuctioneerAddress() {
		return auctioneerAddress;
	}
	public void setAuctioneerAddress(String auctioneerAddress) {
		this.auctioneerAddress = auctioneerAddress;
	}
	public int getNumber() {
		return number;
	}


	public String getDescription() {
		return description;
	}


	public Money getReservePrice() {
		return reservePrice;
	}

	
	

}
