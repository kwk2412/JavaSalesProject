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
		this.bids = bids;
		this.item = item;
		this.currentHighest = null;
		auctionID = nextNum;
		nextNum++;
	}
	
	
	public void loadAuction() {
		
		Bid bid1 = new Bid(150);
		Bid bid2 = new Bid(160);
		Bid bid3 = new Bid(170);
		Bid bid4 = new Bid(180);
		Bid bid5 = new Bid(190);
		Bid bid6 = new Bid(200);
		Bid bid7 = new Bid(210);
		
		bids.enqueue(bid1);
		bids.enqueue(bid2);
		bids.enqueue(bid3);
		bids.enqueue(bid4);
		bids.enqueue(bid5);
		bids.enqueue(bid6);
		bids.enqueue(bid7);
	}
	
	/*
	public void process() {
		if (bids.dequeue().getValue() > currentHighest.getValue() && bids.dequeue().getValue() < the highest amount the customer is wiling to pay) {
			if () 
		}
	}
	*/
	
	
	

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
	
	
	public static void main(String[] args) {
		
		Item fakeGame = new Item(140, "Fake Game", 5);
		Auction auction = new Auction(fakeGame);
		auction.loadAuction();
		
		
		
		
	}

}
