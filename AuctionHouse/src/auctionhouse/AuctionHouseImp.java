/**
 * 
 */
package auctionhouse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Comparator;
import java.util.Collections;

/**
 * @author pbj
 *
 */
public class AuctionHouseImp implements AuctionHouse {
	
	List<Buyer> buyerlist = new ArrayList<Buyer>();
	List <Seller> sellerlist = new ArrayList<Seller>();
	List <Lot> lotlist = new ArrayList<Lot>();
	
	private double buyerPremium;
	private double commission;
	private String houseBankAccount;
	private Money increment;
	private String houseBankAuthCode;
	private BankingService bankingService;
	private MessagingService messagingService;
	
	
	

    private static Logger logger = Logger.getLogger("auctionhouse");
    private static final String LS = System.lineSeparator();
    
    private String startBanner(String messageName) {
        return  LS 
          + "-------------------------------------------------------------" + LS
          + "MESSAGE IN: " + messageName + LS
          + "-------------------------------------------------------------";
    }
    //SETUP 
    public AuctionHouseImp(Parameters parameters) {
        buyerPremium = parameters.buyerPremium;
        commission = parameters.commission;
        increment = parameters.increment;
        houseBankAccount = parameters.houseBankAccount;
        houseBankAuthCode = parameters.houseBankAuthCode;
        messagingService = parameters.messagingService;
        bankingService = parameters.bankingService;
    	
    }
    //REGISTER BUYER
    public Status registerBuyer(
            String name,
            String address,
            String bankAccount,
            String bankAuthCode) {
        logger.fine(startBanner("Registering Buyer: " + name));
        Buyer newBuyer = new Buyer(name, address, bankAccount, bankAuthCode);
        for (Buyer item : buyerlist){
        	if (item.getName() == name) {
        		logger.finer("Buyer is already registered.");
        		return Status.error("Buyer is already registered.");
        	}
        }
        buyerlist.add(newBuyer);
        return Status.OK();
        }

  //REGISTER SELLER
    public Status registerSeller(
            String name,
            String address,
            String bankAccount) {
        logger.fine(startBanner("Registering Seller: " + name));
        Seller newSeller = new Seller(name, address, bankAccount);
        for (Seller item : sellerlist){
        	if (item.getName() == name) {
        		logger.finer("Seller is alread registered.");
        		return Status.error("Seller is already registered.");
        	}
        }
        sellerlist.add(newSeller);
        return Status.OK();      
    }
    //ADD LOT
    public Status addLot(
            String sellerName,
            int number,
            String description,
            Money reservePrice) {
        logger.fine(startBanner("Seller: " + sellerName + " adding LOT ID: " + number));
        //Check if seller name is valid
        int sellerRegistered = 0;
        for (Seller item : sellerlist){
        	if (item.getName() == sellerName) {
        		sellerRegistered =1;
        		break;
        	}
        }
        if (sellerRegistered == 0) {return Status.error("Seller is not registered.");}
        //check if lot number is used
        for (Lot item : lotlist) {
        	if (item.getNumber() == number){
        		return Status.error("Lot number already used");
        	}
        }
        //new Lot
        Lot newlot = new Lot(sellerName,number,description,reservePrice);
        //add to lot list
        lotlist.add(newlot);
        //logger
        logger.fine("Lot ID:"+number+" has been added");
        return Status.OK();    
    }
  //VIEW CATALOGUE
    public List<CatalogueEntry> viewCatalogue() {
        logger.fine(startBanner("Viewing Catalog"));       
        List<CatalogueEntry> catalogue = new ArrayList<CatalogueEntry>();
        for (Lot item: lotlist) {
        	int lotNumber = item.getNumber();
        	String description = item.getDescription();
        	LotStatus lotStatus = item.getLotStatus();
        	CatalogueEntry catalogueEntry = new CatalogueEntry(lotNumber,description,lotStatus);
        	catalogue.add(catalogueEntry);	     	
        }
        //Sorting the catalogue based on lot numbers.
        Collections.sort(catalogue, new Comparator<CatalogueEntry>() {
			public int compare(CatalogueEntry o1, CatalogueEntry o2) {
				return o1.lotNumber - o2.lotNumber;
			}
	    });
        logger.fine("Catalogue: " + catalogue.toString());
        return catalogue;
    }	
    //NOTE INTEREST
    public Status noteInterest(
            String buyerName,
            int lotNumber) {
        logger.fine(startBanner("Buyer: " + buyerName + " is noting interest in LOT ID: " + lotNumber));
        //check lot valid
        int lotValid = 0;
        for (Lot item : lotlist) {
        	if (item.getNumber() == lotNumber){
        		//if lot valid check not in auction
        		if (item.getLotStatus() == LotStatus.UNSOLD) {
        			lotValid = 1;
        		}else {
        			return Status.error("LOT ID: " +lotNumber+" is"+item.getLotStatus().toString());
        		}
        	}
        }
        if (lotValid == 0) {return Status.error("Lot ID not valid");}
        //check if buyer registered
        int buyerValid = 0;
        for (Buyer item : buyerlist) {
        	if (item.getName()==buyerName) {
        		buyerValid = 1;
        	}
        }
        if (buyerValid==0) {return Status.error("Buyer not registered");}
        //get lot, get buyer, add buyer to lot noteInterest
        for (Lot item : lotlist) {
        	if (item.getNumber() == lotNumber){
        		for (Buyer abuyer : buyerlist) {
                	if (abuyer.getName()==buyerName) {
                		item.noteInterest(abuyer);
                		break;
                	}
        		}
        		break;
        	}
        }
        //logger
        logger.fine("Buyer: "+buyerName+" Interested Lot ID: " +lotNumber);
        return Status.OK();
        
    }
    //OPEN AUCTION
    public Status openAuction(
            String auctioneerName,
            String auctioneerAddress,
            int lotNumber) {
        logger.fine(startBanner("Auctioneer " + auctioneerName + " Opens LOT ID: " + lotNumber));
        //validate Lot Number
        int lotValid = 0;
        Lot theLot = null;
        for (Lot item : lotlist) {
        	if (item.getNumber() == lotNumber){
        		lotValid = 1;
        		theLot = item;
        	}
        }
        if (lotValid == 0) {return Status.error("Lot ID not valid");}
        //if Lot status is not UNSOLD
        if (theLot.getLotStatus()!= LotStatus.UNSOLD) {
        	return Status.error("LOT ID: " +lotNumber+" is"+theLot.getLotStatus().toString());
        }
        //set LOT to IN_AUCTION
        theLot.setLotStatus(LotStatus.IN_AUCTION);
        theLot.setAuctioneerName(auctioneerName);
        theLot.setAuctioneerAddress(auctioneerAddress);
        //Send messages to all interested buyers
        for (Buyer item : theLot.getNoteInterestList()) {
        	messagingService.auctionOpened(item.getAddress(), lotNumber);  	
        }
        //Send message to seller
        for (Seller seller :sellerlist) {
        	if (seller.getName()==theLot.getSellerName()){
        		messagingService.auctionOpened(seller.getAddress(), lotNumber);  	
        	}
        }
    
        
        return Status.OK();
    }
    //MAKE BID
    public Status makeBid(
            String buyerName,
            int lotNumber,
            Money bid) {
        logger.fine(startBanner("makeBid " + buyerName + " " + lotNumber + " " + bid));
        //validate Lot Number
        int lotValid = 0;
        Lot theLot = null;
        for (Lot item : lotlist) {
        	if (item.getNumber() == lotNumber){
        		lotValid = 1;
        		theLot = item;
        	}
        }
        if (lotValid == 0) {
        	logger.fine("Lot ID " + lotNumber + " is not valid");
        	return Status.error("Lot ID " + lotNumber + " is not valid");}
      //check if buyer registered
        int buyerValid = 0;
        Buyer theBuyer = null;
        for (Buyer item : buyerlist) {
        	if (item.getName()==buyerName) {
        		theBuyer = item;
        		buyerValid = 1;
        	}
        }
        if (buyerValid==0) {
        	logger.fine("Buyer: " + buyerName + " not registered");
        	return Status.error("Buyer: " + buyerName + " not registered");}
        //check if lot in auction
        if (theLot.getLotStatus()!= LotStatus.IN_AUCTION) {
        	logger.fine("LOT ID: " +lotNumber+" is"+theLot.getLotStatus().toString());
        	return Status.error("LOT ID: " +lotNumber+" is"+theLot.getLotStatus().toString());
        }
        //if there is no current bid
        Money zero = new Money("0.00");
        if (theLot.getCurrentBid().equals(zero)) {
        	theLot.setCurrentBid(bid);
        	theLot.setCurrentBuyerName(buyerName);

            logger.fine(startBanner("Bid0 Placed by: " + buyerName + " on LOT ID: " + lotNumber + " AMOUNT: " + bid));
           

        }else {
        	//if there is a current bid
        	logger.fine(startBanner("New Bid Placed by: " + buyerName + " on LOT ID: " + lotNumber + " AMOUNT: " + bid));
        	if (theLot.getCurrentBid().add(increment).lessEqual(bid)) {
            	theLot.setCurrentBid(bid);
            	theLot.setCurrentBuyerName(buyerName);
            	logger.fine(startBanner("Bid1 Placed by: " + buyerName + " on LOT ID: " + lotNumber + " AMOUNT: " + bid));
        	}else {
        		logger.fine("Your bid: " +bid.toString()+" is less than "+theLot.getCurrentBid().add(increment).toString());
        		return Status.error("Your bid: " +bid.toString()+" is less than "+theLot.getCurrentBid().add(increment).toString());

        	}
        	
        }
    	//sending messages
    	for (Buyer item : theLot.getNoteInterestList()) {
    		if (item.getName()!=buyerName) {
    			messagingService.bidAccepted(item.getAddress(), lotNumber, bid);
    		}
    	}
    	
    	messagingService.bidAccepted(theLot.getAuctioneerAddress(), lotNumber, bid);

    	for (Seller item : sellerlist) {
    		if (item.getName()==theLot.getSellerName()) {
    			messagingService.bidAccepted(item.getAddress(), lotNumber, bid);
    		}
    	}
    	
    	logger.fine(startBanner("biddone"));
        return Status.OK();    
    }

    public Status closeAuction(
            String auctioneerName,
            int lotNumber) {
        logger.fine(startBanner("closeAuction " + auctioneerName + " " + lotNumber));
 
        return Status.OK();  
    }
}
