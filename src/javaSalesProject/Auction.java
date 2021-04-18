package javaSalesProject;

import java.time.LocalDateTime;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author waveo Auctions will be filled with bid objects
 *
 */
public class Auction implements Comparable<Auction>{

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY h:mm a");
	NumberFormat cf = NumberFormat.getCurrencyInstance();
	private Stack<Bid> processedBids = new Stack<>(); // Stack of bids for keeping track of bids that are part of an
														// active auction. Also for record keeping
	private Queue<Bid> unprocessedBids = new Queue<>(); // This is a queue full of bids yet to be processed in the
														// auction
	private Item item;
	private Bid currentHighest; // bid with highest max value willing to pay lives here
	private double currentSalesPrice;
	private int increment;
	private boolean active;

	private int auctionID;
	private static int nextNum = 1000;

	private int numBids = 0;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	// how to keep track of the window for which the auction will be active
	// LocalDate date = new LocalDate();

	public Auction() {
		auctionID = nextNum;
		nextNum++;
	}

	public Auction(Item item) {
		this.item = item;
	}
	
	public Auction(Item item, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.item = item;
		currentHighest = null;
		currentSalesPrice = item.getStartingPrice();
		increment = item.getIncrement();
		auctionID = nextNum;
		Driver.items.remove(item);
		nextNum++;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public Auction(Item item, int auctionID) {
		this.item = item;
		this.auctionID = auctionID;
		currentHighest = null;
		currentSalesPrice = item.getStartingPrice();
		increment = item.getIncrement();
		//Driver.items.remove(item);
		active = true;
	}
	
	public Auction(Item item, int auctionID, double currentSalesPrice) {
		this.item = item;
		this.auctionID = auctionID;
		currentHighest = null;
		this.currentSalesPrice = currentSalesPrice;
		increment = item.getIncrement();
		//Driver.items.remove(item);
		active = true;
	}
	

	public String toString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + item.selectAuctionToString() + "\tCurrent Sales Price: "
				+ cf.format(currentSalesPrice) + "\n" + "\tStart Time: " + dtf.format(startDateTime) + "\n\tEnd Time: " + dtf.format(endDateTime) + "\n";
		if (currentHighest != null) {
			result += "\tCurrent high bidder: " + currentHighest.getCustomer().getUsername() + "\n";
			result += "\tMax current high bidder is willing to pay: " + cf.format(currentHighest.getValue()) + "\n";
		}
		return result;
	}

	public String custVersionToString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + item.selectAuctionToString() + "\tCurrent Sales Price: "
				+ cf.format(currentSalesPrice) + "\n"+ "\tStart Time: " + dtf.format(startDateTime) + "\n\tEnd Time: " + dtf.format(endDateTime);
		if (currentHighest != null) {
			if (Driver.currentUser.getUser().getUserID() == currentHighest.getCustomer().getUserID()) {
				result += "\tYou are the high bidder\n";
			} else {
				result += "\tCurrent high bidder: " + currentHighest.getCustomer().getUsername() + "\n";
			}
			result += "\tMax current high bidder is willing to pay: " + cf.format(currentHighest.getValue()) + "\n";
		}
		return result;
	}

	// Something is eliminating an invalid bid and the following one
	// causing the out of bounds exception when you go to continue the auction
	// without any bids left to process

	public void automateAuction() {

		while (unprocessedBids.size() > 0) {
			process(unprocessedBids.dequeue());
		}
	}

	public void process(Bid bid) {
		if (currentHighest == null)
			firstBid(bid);
		else if (isValid(bid)) {
			if (bid.getValue() <= currentHighest.getValue()) {
				currentSalesPrice = bid.getValue();
			} else {
				currentSalesPrice = currentHighest.getValue();
				currentHighest = bid;
			}
			bid.getCustomer().addCurrentBid(bid); // Add bid to Customer's list of bids
			processedBids.push(bid); // Add list to Auction's list of bids
			numBids++;
		} else {
			System.out.println("Invalid bid");
		}
	}

	public boolean isValid(Bid bid) {
		if (bid.getValue() >= (currentSalesPrice + increment)) {
			bid.setValid(true);
			return bid.isValid();
		} else {
			return false;
		}
	}

	public void firstBid(Bid bid) {
		if (bid.getValue() >= currentSalesPrice) {
			bid.getCustomer().addCurrentBid(bid); // Add bid to Customer's list of bids
			processedBids.push(bid); // Add list to Auction's list of bids
			currentHighest = processedBids.peek();
			numBids++;
			bid.setValid(true);
		} else {
			System.out.println("First bid must be at least the sales price of the item\n");
		}
	}

	public void endAuction() {
		active = false;
		//System.out.println("Auction over");
		clearActiveBids();
		Driver.completedAuctions.add(this);
		Driver.ongoingAuctions.remove(this);
		if (currentHighest != null) {
			//System.out.println("The winner is " + currentHighest.getCustomer().usernameIdString());
			currentHighest.getCustomer().addWinningBid(currentHighest);
		}
	}

	
	public void clearActiveBids() {
		Stack<Bid> copy = new Stack<>();
		copy = processedBids;

		while (copy.size() > 0) {
			Bid b = copy.pop();
			b.getCustomer().removeActiveBid(b);
		}

		/*
		 * System.out.
		 * println("To ensure that the original stack was untouched in the process of clearing active bids"
		 * ); for (int i = processedBids.size(); i > 0; i--) {
		 * System.out.println(processedBids.pop().toString()); }
		 */
	}

	public void printAuctionStatus() {
		System.out.println("Item: " + item.getName());
		if (currentHighest != null) {
			System.out.println("Highest Bid: " + cf.format(currentHighest.getValue()));
		} else {
			System.out.println("Highest Bid: none");
		}
		System.out.println("Selling Price: " + cf.format(currentSalesPrice));
		System.out.println("Increment: " + cf.format(increment));
		System.out.println("Active: " + active);
		System.out.println("Auction ID: " + auctionID);
		System.out.println("Number of Bids: " + numBids);
		System.out.println();
	}
	

	private void clearProcessedBids() {
		processedBids.clear();
	}

	public void loadQueue(Bid bid) {
		unprocessedBids.enqueue(bid);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public double getCurrentSalesPrice() {
		return currentSalesPrice;
	}

	public int getAuctionID() {
		return auctionID;
	}

	public void setSellingPrice(double sellingPrice) {
		this.currentSalesPrice = sellingPrice;
	}

	public Stack<Bid> getBids() {
		return processedBids;
	}

	public void setBids(Stack<Bid> bids) {
		this.processedBids = bids;
	}

	public Stack<Bid> getProcessedBids() {
		return processedBids;
	}

	public void setProcessedBids(Stack<Bid> processedBids) {
		this.processedBids = processedBids;
	}

	public Queue<Bid> getUnprocessedBids() {
		return unprocessedBids;
	}

	public void setUnprocessedBids(Queue<Bid> unproccessedBids) {
		this.unprocessedBids = unproccessedBids;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	@Override
	public int compareTo(Auction o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
