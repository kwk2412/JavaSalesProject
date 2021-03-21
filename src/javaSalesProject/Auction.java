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
	private final int BIDSALLOWED = 3;
	
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

	/*
	// returns true if the bid is accepted. Returns false if the bid is rejected
	public boolean processBid(Bid theNewBid) {
		boolean validBid = false;
		// first valid bid. Sales price doesn't change
		if (currentHighest == null) {
			if (theNewBid.getValue() >= currentSalesPrice) {
				currentHighest = theNewBid;
				System.out.println("You are now the highest bidder.");
				++numBids;
				validBid = true;
			} else {
				System.out.println("Your bid was too low. It has been rejected");
			}
		}
		
		// The current highest is not equal to null and the new bid is at least an increment greater than the sales price
		else if (theNewBid.getValue() - currentSalesPrice >= increment) {
			validBid = true;
			// theNewBid becomes the new currentHighest
			// current sales price is set to an increment greater than the current highest
			if(theNewBid.getValue() - currentHighest.getValue() >= increment) {
				currentSalesPrice = currentHighest.getValue() + increment;
				currentHighest = theNewBid;
				++numBids;
				System.out.println("Sales price increased to " + cf.format(currentSalesPrice));
				System.out.println("You are now the highest bidder");
				
			} 
			// currentHighest stays the currentHighest
			// current sales price is set to an increment greater than theNewBid
			else if(currentHighest.getValue() - theNewBid.getValue() >= increment) {
				currentSalesPrice = theNewBid.getValue() + increment;
				++numBids;
				System.out.println("Sales price increased to " + cf.format(currentSalesPrice));
				System.out.println("You are not the highest bidder");
				
			}
			
			// the newNew bid and currentHighest are not an increment different from one another
			// The currentSalesPrice gets set the currentHighest
			else {
				currentSalesPrice = currentHighest.getValue();
				++numBids;
				System.out.println("Sales price increased to " + cf.format(currentSalesPrice));
				System.out.println("You are not the highest bidder");
				
			}
			
		} else {
			System.out.println("Your bid was too low. It has been rejected");
		}
		
		System.out.println("There are " + (BIDSALLOWED - numBids) + " bids left in this auction");
		
		if(validBid) {
			processedBids.push(theNewBid);
		}
		
		if(numBids >= BIDSALLOWED) {
			endAuction();
		}
		return validBid;
	}
	
	*/
	
	
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
			System.out.println("Invalid Bid: Bid cannot be between the current highest bid and an increment up from that");
			// Throw new InvalidRange();
			return false;
		}
		if (bid.getValue() >= currentSalesPrice && bid.getValue() < currentSalesPrice + increment) {
			System.out.println("Invalid Bid: Bid cannot be between current sales price and sales price + increment");
			return false;
		}
		if (bid.getValue() < currentSalesPrice) {
			return false;
		}
		bid.setValid(true);
		return bid.isValid();
	}
	
	
	public void firstBid(Bid bid) {
		if (bid.getValue() >= currentSalesPrice + increment) {
			bid.getCustomer().addCurrentBid(bid);	// Add bid to Customer's list of bids
			processedBids.push(bid);	// Add list to Auction's list of bids
			currentHighest = processedBids.peek();
			numBids++;
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
	
	//This algorithm needs to be fixed, Kevin tried but made a mess
	// This is supposed to remove the active bids from the activeBids arraylist 
	// of all customers that participated in the auction. but it doesn't do that 
	public void clearActiveBids() {
		Stack<Bid> copyBids = processedBids;
		for (int i = copyBids.size(); i < 0; i--) {
			if (copyBids.pop().getCustomer().getActiveBids().get(i).getAuction() == this) {
				copyBids.pop().getCustomer().getActiveBids().remove(copyBids.pop().getCustomer().getActiveBids().get(i));
			}
		}	
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
