package javaSalesProject;

import java.sql.Timestamp;
import java.text.NumberFormat;

public class Bid {

	NumberFormat cf = NumberFormat.getCurrencyInstance();
	private double value;
	private Auction auction;
	private boolean valid;
	private Customer customer;

	private int time;


	public Bid(double value) {
		this.value = value;
	}

	public Bid(double value, Auction auction, Customer customer) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
		this.valid = false;
	}
	

	public Bid(double value, Auction auction, Customer customer, int time) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
		this.valid = false;
		this.time = time;
	}


	public String toString() {
		return "\tItem name: " + auction.getItem().getName() + "\n" + 
				"\tCustomer Username: " + customer.getUsername() + "\n" +
				"\tBid amount: " + cf.format(value) + "\n" +
				"\tAuction ID: " + auction.getAuctionID() + "\n";
	}

	public String toStringActive() {
		String result = "Item name: " + auction.getItem().getName() + "\n" +
				"AuctionID: " + auction.getAuctionID() + "\n" +
				"Bid amount: " + cf.format(value) + "\n" + 
				"Current sales price: " + cf.format(auction.getCurrentSalesPrice());
		
		if (auction.getCurrentHighest().getCustomer().getUserID() == customer.getUserID()) {
			result += "\nYou are the highest bidder";
		}
		else {
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
		return "Item name: " + auction.getItem().getName() + "\n" + 
				"Bid amount: " + cf.format(value) + "\n" +
				"AuctionID: " + auction.getAuctionID() + "\n";
	}
	
	public boolean equals(Bid b) {
		if (b == this) return true;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
