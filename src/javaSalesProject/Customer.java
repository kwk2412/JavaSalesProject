package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Account {
	
	private ArrayList<Bid> currentBids = new ArrayList<Bid>();
	private ArrayList<Bid> winningBids = new ArrayList<Bid>();
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String privileges) {
		super(username, password, privileges);
		
	}
	
	// Constructor used when (re)creating accounts imported from a text file
	public Customer(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);
		
	}

	
	public void addBid(Bid bid) {
		currentBids.add(bid);
	}
	
	public void removeBid(Bid bid) {
		currentBids.remove(bid);
	}
	
	public void placeBid() {
		
	}

	
	public ArrayList<Bid> getCurrentBids() {
		return currentBids;
	}


	public void setCurrentBids(ArrayList<Bid> currentBids) {
		this.currentBids = currentBids;
	}

	public ArrayList<Bid> getWinningBids() {
		return winningBids;
	}

	public void setWinningBids(ArrayList<Bid> winningBids) {
		this.winningBids = winningBids;
	}
	
}

