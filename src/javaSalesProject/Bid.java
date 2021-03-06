package javaSalesProject;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bid {


	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY h:mm a");
	NumberFormat cf = NumberFormat.getCurrencyInstance();
	private double value;
	private Auction auction;
	private boolean valid;
	private Customer customer;
	private int bidID;
	
	protected static int nextNum = 500;
	private LocalDateTime dateTime;


	public Bid(double value) {
		this.value = value;
	}

	public Bid(double value, Auction auction, Customer customer) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
		this.valid = false;
		this.bidID = nextNum;
		nextNum++;
		this.dateTime = LocalDateTime.now();

	}
	
	public Bid(double value, Auction auction, int userID) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = findCustomer(userID);
		this.valid = false;
		this.bidID = nextNum;
		nextNum++;
	}
	
	public Bid(double value, Auction auction, Customer customer, int bidID) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
		this.valid = false;
		this.bidID = bidID;
	}

	public Bid(double value, Auction auction, Customer customer, LocalDateTime dateTime) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
		this.valid = false;
		this.bidID = bidID;
		this.dateTime = dateTime;
	}


	public String toString() {
		return "\tItem name: " + auction.getItem().getName() + "\n" + 
				"\tCustomer Username: " + customer.getUsername() + "\n" +
				"\tBid amount: " + cf.format(value) + "\n" +
				"\tAuction ID: " + auction.getAuctionID() + "\n" + 
				"\tTime of bid: " + dtf.format(dateTime) + "\n";
	}

	public String toStringActive() {
		String result = "Item name: " + auction.getItem().getName() + "\n" +
				"AuctionID: " + auction.getAuctionID() + "\n" +
				"Bid amount: " + cf.format(value) + "\n" + 
				"Current sales price: " + cf.format(auction.getCurrentSalesPrice()) + 
				"\tTime of bid: " + dtf.format(dateTime) + "\n";
		
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
				+ cf.format(auction.getCurrentSalesPrice()) + "\n" + "Time of bid:  " + dtf.format(dateTime) + "\n";

		return result;
	}

	public String toStringWinningBid() {
		return "Item name: " + auction.getItem().getName() + "\n" + 
				"Bid amount: " + cf.format(value) + "\n" +
				"AuctionID: " + auction.getAuctionID() + "\n" + 
				"Time of bid:  " + dtf.format(dateTime) + "\n";
	}
	
	public Customer findCustomer(int userID) {
		for (int i = 0; i < Driver.accounts.size(); i++) {
			if (Driver.accounts.get(i).userID == userID) {
				return (Customer) Driver.accounts.get(i);
			}
		}
		return null;
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
	
	
	public int getBidID() {
		return bidID;
	}

	public void setBidID(int bidID) {
		this.bidID = bidID;
	}
	
}
