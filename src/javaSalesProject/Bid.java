package javaSalesProject;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Bid implements Comparable<Bid> {

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
		this.bidID = findNextNum();
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
	
	public Bid(double value, Auction auction, Customer customer, int bidID, LocalDateTime dateTime) {
		super();
		this.value = value;
		this.auction = auction;
		this.customer = customer;
		this.valid = true;
		this.bidID = bidID;
		this.dateTime = dateTime;
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
				"\tBid ID: " + getBidID() + "\n" +
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
	
	public int findNextNum() {
		ArrayList<Bid> bids = gatherBids();
		return highest(bids) + 1;
	}
	
	public ArrayList<Bid> gatherBids() {
		ArrayList<Bid> bids = new ArrayList<>();
		if (!Driver.completedAuctions.isEmpty()) {
			for (int i = 0; i < Driver.completedAuctions.size(); i++) {
				for (int j = 0; j < Driver.completedAuctions.get(i).getProcessedBids().size(); j++) {
					Stack<Bid> clone = Driver.completedAuctions.get(i).getProcessedBids().clone();
					while (clone.size() > 0) {
						bids.add(clone.pop());
					}
				}
			}
		}

		if (!Driver.ongoingAuctions.isEmpty()) {
			for (int i = 0; i < Driver.ongoingAuctions.size(); i++) {
				for (int j = 0; j < Driver.ongoingAuctions.get(i).getProcessedBids().size(); i++) {
					Stack<Bid> clone = Driver.ongoingAuctions.get(i).getProcessedBids().clone();
					while (clone.size() > 0) {
						bids.add(clone.pop());
					}
				}
				for (int j = 0; j < Driver.ongoingAuctions.get(i).getUnprocessedBids().size(); i++) {
					Queue<Bid> clone = Driver.ongoingAuctions.get(i).getUnprocessedBids().clone();
					while (clone.size() > 0) {
						bids.add(clone.dequeue());
					}
				}
			}
		}
		return bids;
	}
	
	public int highest(ArrayList<Bid> bids) {
		int highest = 0;
		for (int i = 0; i < bids.size(); i++) {
			if (bids.get(i).getBidID() > highest) {
				highest = bids.get(i).getBidID();
			}
		}
		return highest;
	}

	public int compareTo(Bid b) {
		if (b.bidID > this.bidID) {
			return 1;
		}
		else if (b.bidID < this.bidID) {
			return -1;
		}
		else 
			return 0;
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
}
