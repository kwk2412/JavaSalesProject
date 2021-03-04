package javaSalesProject;

import java.sql.Timestamp;

public class Bid {
	
	
	private double value;
	private int auctionID;
	
	// Driver.currentUser.getUser()
	// Only the currently active user can place a bid so that is where the bid creation will come from
	private Customer customer;
	
	// Timestamp must be an instance variable
	// LocalDate()
	// Timestamp timestamp = new Timestamp(auctionID, auctionID, auctionID, auctionID, auctionID, auctionID, auctionID);
	
	
	
	public Bid(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getAuctionID() {
		return auctionID;
	}

	public void setAuctionID(int auctionID) {
		this.auctionID = auctionID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
