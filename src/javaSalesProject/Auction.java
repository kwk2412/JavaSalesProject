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
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Item item;
	private Bid currentHighest; // bid with highest max value willing to pay lives here
	private double currentSalesPrice;
	private int increment;

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
		currentSalesPrice = item.getStartingPrice();
		increment = item.getIncrement();
		currentHighest = null;
		this.item = item;
		auctionID = nextNum;
		Driver.items.remove(item);
		nextNum++;
	}
	
	public String toString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + item.selectAuctionToString() + "\tCurrent Sales Price: " + cf.format(currentSalesPrice) + "\n";
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
			if(Driver.currentUser.getUser().getUserID() == currentHighest.getCustomer().getUserID()) {
				result += "\tYou are the high bidder\n";
			} else {
			result += "\tCurrent high bidder: " + currentHighest.getCustomer().getUsername() + "\n";
			}
			result += "\tMax current high bidder is willing to pay: " + cf.format(currentHighest.getValue()) + "\n";
		}
		return result;
	}

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
			bids.add(theNewBid);
		}
		
		if(numBids >= BIDSALLOWED) {
			System.out.println("Auction over");
			System.out.println("The winner is " + currentHighest.getCustomer().usernameIdString());
			clearActiveBids();
			Driver.completedAuctions.add(this);
			Driver.ongoingAuctions.remove(this);
			currentHighest.getCustomer().addWinningBid(currentHighest);

		}
		return validBid;
	}
	
	private void clearActiveBids() {
		for(Bid b: bids) {
			b.getCustomer().removeActiveBid(b);
		}
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

	public double getcurrentSalesPrice() {
		return currentSalesPrice;
	}

	public int getAuctionID() {
		return auctionID;
	}

	public void setSellingPrice(double sellingPrice) {
		this.currentSalesPrice = sellingPrice;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}

}
