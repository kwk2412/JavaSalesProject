package javaSalesProject;

import java.sql.Timestamp;
import java.text.NumberFormat;

public class Bid {

	NumberFormat cf = NumberFormat.getCurrencyInstance();
	private double value;
	private Auction auction;

	// Driver.currentUser.getUser()
	// Only the currently active user can place a bid so that is where the bid
	// creation will come from
	private Customer customer;

	// Timestamp must be an instance variable
	// LocalDate()
	// Timestamp timestamp = new Timestamp(auctionID, auctionID, auctionID,
	// auctionID, auctionID, auctionID, auctionID);

	public Bid(double value) {
		this.value = value;
	}

	public Bid(double value, Auction auction, Customer customer) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
	}

	public String toString() {
		return "Item name: " + auction.getItem().getName() + "\nCustomer Username: " + customer.getUsername()
				+ "\nBid amount: " + cf.format(value) + "\nAuction ID: " + auction.getAuctionID() + "\n";
	}

	public String toStringActive() {
		String result = "Item name: " + auction.getItem().getName() + "\nAuctionID: " + auction.getAuctionID()
				+ "\nBid amount: " + cf.format(value) + "\n" + "Current sales price: "
				+ cf.format(auction.getCurrentSalesPrice());
		if (auction.getCurrentHighest().getCustomer().getUserID() == customer.getUserID()) {
			result += "\nYou are the highest bidder";
		} else {
			result += "\n" + auction.getCurrentHighest().getCustomer().getUsername() + " is the highest bidder"
					+ "\nThe max the highest bidder is willing to pay is "
					+ cf.format(auction.getCurrentHighest().getValue());
		}
		result += "\n";
		return result;
	}

	public String toStringWinning() {
		String result = "Item name: " + auction.getItem().getName() + "\nAuctionID: " + auction.getAuctionID()
				+ "\nBid amount: " + cf.format(value) + "\nFinal sales price: "
				+ cf.format(auction.getCurrentSalesPrice()) + "\n";

		return result;
	}

	public String toStringWinningBid() {
		return "Item name: " + auction.getItem().getName() + "\nBid amount: " + cf.format(value) + "\nAuctionID: "
				+ auction.getAuctionID() + "\n";
	}
	
	public boolean equals(Bid b) {
		if(b.getValue() == this.value)
		{
			if(b.getCustomer().userID == this.getCustomer().userID) {
				if(b.getAuction().getAuctionID() == this.getAuction().getAuctionID()) {
					return true;
				}
			}
		}
		return false;
	}


	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
