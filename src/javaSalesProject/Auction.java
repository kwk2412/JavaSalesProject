package javaSalesProject;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 
 * @author waveo
 * Auctions will be filled with bid objects
 *
 */
public class Auction {

	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Item item;
	private Bid currentHighest;
	private double sellingPrice;	
	
	private int auctionID;
	private int nextNum = 100;
	
	//how to keep track of the window for which the auction will be active
	//LocalDate date = new LocalDate();
	
	
	public Auction() {
		auctionID = nextNum;
		nextNum++;
	}
	
	
	public Auction(Item item) {
		this.item = item;
		auctionID = nextNum;
		nextNum++;
	}
	
	
	
	


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}


	public Bid getCurrentHighest() {
		return currentHighest;
	}


	public void setCurrentHighest(Bid currentHighest) {
		this.currentHighest = currentHighest;
	}


	public double getSellingPrice() {
		return sellingPrice;
	}


	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}


	public ArrayList<Bid> getBids() {
		return bids;
	}


	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}

}
