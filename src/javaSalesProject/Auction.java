package javaSalesProject;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 
 * @author waveo Auctions will be filled with bid objects
 *
 */
public class Auction {

	
	NumberFormat cf = NumberFormat.getCurrencyInstance();
	private Stack<Bid> processedBids = new Stack<>();	// Stack of bids for keeping track of bids that are part of an active auction. Also for record keeping
	private Queue<Bid> unprocessedBids = new Queue<>();	// This is a queue full of bids yet to be processed in the auction
	private Item item;
	private Bid currentHighest; // bid with highest max value willing to pay lives here
	private double currentSalesPrice;
	private int increment;
	private boolean active;

	private int auctionID;
	private static int nextNum = 100;
	private int numBids = 0;

	private final int BIDSALLOWED = 10;

	

	// how to keep track of the window for which the auction will be active
	// LocalDate date = new LocalDate();	
	

	
	public Auction() {
		auctionID = nextNum;
		nextNum++;
	}

	public Auction(Item item) {
		this.item = item;
		currentHighest = null;
		currentSalesPrice = item.getStartingPrice();
		increment = item.getIncrement();
		auctionID = nextNum;
		Driver.items.remove(item);
		nextNum++;
		active = true;
	}
	
	public String toString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + 
				item.selectAuctionToString() + "\tCurrent Sales Price: " + cf.format(currentSalesPrice) + "\n";
		if(currentHighest != null) {
			result += "\tCurrent high bidder: " + currentHighest.getCustomer().getUsername() + "\n";
			result += "\tMax current high bidder is willing to pay: " + cf.format(currentHighest.getValue()) + "\n";
		}
		return result;
	}
	
	public String custVersionToString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + item.selectAuctionToString() + "\tCurrent Sales Price: " + cf.format(currentSalesPrice) + "\n";
		if(currentHighest != null) {
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
		if (currentHighest == null) firstBid(bid);
		else if (isValid(bid)) {
			if (bid.getValue() > currentHighest.getValue()) {
				currentSalesPrice = currentHighest.getValue() + increment;
				currentHighest = bid;
			}
			else {
				currentSalesPrice = bid.getValue() + increment;
			}
			bid.getCustomer().addCurrentBid(bid);	// Add bid to Customer's list of bids
			processedBids.push(bid);	// Add list to Auction's list of bids
			numBids++;
		}
		else {
			System.out.println("Invalid bid");
		}
		checkEnd();
	}
	

	



	public boolean isValid(Bid bid) {
		if (bid.getValue() >= currentHighest.getValue()  && bid.getValue() < currentHighest.getValue() + increment) {
			System.out.println("Invalid Bid: Bid cannot be between the current highest bid and an increment up from that\n");
			return false;
		}
		if (bid.getValue() >= currentSalesPrice && bid.getValue() < currentSalesPrice + increment) {
			System.out.println("Invalid Bid: Bid cannot be between current sales price and sales price + increment\n");
			return false;
		}
		if (bid.getValue() < currentSalesPrice) {

			System.out.println("Invalid Bid: Bid cannot be less than the current sales price");

			return false;
		}
		
		bid.setValid(true);
		return bid.isValid();
	}
	
	
	public void firstBid(Bid bid) {
		if (bid.getValue() >= currentSalesPrice) {
			bid.getCustomer().addCurrentBid(bid);	// Add bid to Customer's list of bids
			processedBids.push(bid);	// Add list to Auction's list of bids
			currentHighest = processedBids.peek();
			numBids++;
			bid.setValid(true);
		}
		else {
			System.out.println("First bid must at least be higher than the sales price of the item + increment\n");
		}
	}
	
	public void checkEnd() {
		if (numBids >= BIDSALLOWED) {
			endAuction();
		}
	}
	
	public void endAuction() {
		active = false;
		System.out.println("Auction over");
		System.out.println("The winner is " + currentHighest.getCustomer().usernameIdString());
		clearActiveBids();
		Driver.completedAuctions.add(this);
		Driver.ongoingAuctions.remove(this);
		currentHighest.getCustomer().addWinningBid(currentHighest);
	}
	
	
	public void clearActiveBids() {
		Stack<Bid> copy = new Stack<>();
		copy = processedBids;
		
		while (copy.size() > 0) {
			Bid b = copy.pop();
			b.getCustomer().removeActiveBid(b);
		}
		
		/*
		System.out.println("To ensure that the original stack was untouched in the process of clearing active bids");
		for (int i = processedBids.size(); i > 0; i--) {
			System.out.println(processedBids.pop().toString());
		}
		*/
	}

	
	public void printAuctionStatus() {
		System.out.println("Item: " + item.getName());
		if (currentHighest != null) {
			System.out.println("Highest Bid: " + cf.format(currentHighest.getValue()));
		}
		else {
			System.out.println("Highest Bid: none");
		}
		System.out.println("Selling Price: " + cf.format(currentSalesPrice));
		System.out.println("Increment: " + cf.format(increment));
		System.out.println("Active: " + active);
		System.out.println("Auction ID: " + auctionID);
		System.out.println("Number of Bids: " + numBids);
		System.out.println("Bids Allowed: " + BIDSALLOWED);
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


	public Queue<Bid> getUnprocessedBids() {
		return unprocessedBids;
	}

	public void setUnprocessedBids(Queue<Bid> unproccessedBids) {
		this.unprocessedBids = unproccessedBids;
	}

}
