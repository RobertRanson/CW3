/**
 * 
 */
package auctionhouse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author pbj
 *
 */
public class AuctionHouseImp implements AuctionHouse {
	
	List<Buyer> buyerlist = new ArrayList<Buyer>();
	List <Seller> sellerlist = new ArrayList<Seller>();
	List <Lot> lotlist = new ArrayList<Lot>();
	
	
	

    private static Logger logger = Logger.getLogger("auctionhouse");
    private static final String LS = System.lineSeparator();
    
    private String startBanner(String messageName) {
        return  LS 
          + "-------------------------------------------------------------" + LS
          + "MESSAGE IN: " + messageName + LS
          + "-------------------------------------------------------------";
    }
   
    public AuctionHouseImp(Parameters parameters) {
    	
    }

    public Status registerBuyer(
            String name,
            String address,
            String bankAccount,
            String bankAuthCode) {
        logger.fine(startBanner("registerBuyer " + name));
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


    public Status registerSeller(
            String name,
            String address,
            String bankAccount) {
        logger.fine(startBanner("registerSeller " + name));
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

    public Status addLot(
            String sellerName,
            int number,
            String description,
            Money reservePrice) {
        logger.fine(startBanner("addLot " + sellerName + " " + number));
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

        //seller registers lot
        //add to catalogue
        //logger.fine(lot with lot id 123 was added to catalogue)
        return Status.OK();    
    }

    public List<CatalogueEntry> viewCatalogue() {
        logger.fine(startBanner("viewCatalog"));
        
        List<CatalogueEntry> catalogue = new ArrayList<CatalogueEntry>();
        logger.fine("Catalogue: " + catalogue.toString());
        return catalogue;
    }

    public Status noteInterest(
            String buyerName,
            int lotNumber) {
        logger.fine(startBanner("noteInterest " + buyerName + " " + lotNumber));
        
        return Status.OK();   
    }

    public Status openAuction(
            String auctioneerName,
            String auctioneerAddress,
            int lotNumber) {
        logger.fine(startBanner("openAuction " + auctioneerName + " " + lotNumber));
        
        return Status.OK();
    }

    public Status makeBid(
            String buyerName,
            int lotNumber,
            Money bid) {
        logger.fine(startBanner("makeBid " + buyerName + " " + lotNumber + " " + bid));

        return Status.OK();    
    }

    public Status closeAuction(
            String auctioneerName,
            int lotNumber) {
        logger.fine(startBanner("closeAuction " + auctioneerName + " " + lotNumber));
 
        return Status.OK();  
    }
}
