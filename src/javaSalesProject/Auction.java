package javaSalesProject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 
 * @author waveo
 * Auctions will be filled with bid objects
 *
 */
public class Auction {

	
	//This is what will likely be turned into a queue
	private Queue<Bid> bids = new Queue<>();
	private Item item;
	private Bid currentHighest;
	private double sellingPrice;	
	
	private int auctionID;
	private int nextNum = 100;
	
	// How to keep track of the window for which the auction will be active
	//private LocalDate creationTime = new LocalDate(1999, 8, 17);
	
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


	public Queue<Bid> getBids() {
		return bids;
	}


	public void setBids(Queue<Bid> bids) {
		this.bids = bids;
	}

}
