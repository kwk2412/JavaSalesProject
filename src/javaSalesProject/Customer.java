package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Account {

	private ArrayList<Bid> activeBids = new ArrayList<Bid>();
	private ArrayList<Bid> winningBids = new ArrayList<Bid>();

	public Customer() {
		super();
	}

	public Customer(String username, String password, String privileges) {
		super(username, password, privileges);
		Driver.accounts.add(this);
	}

	// Constructor used when (re)creating accounts imported from a text file
	public Customer(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);

	}

	public String activeBidsToString() {

		if (activeBids.size() == 0) {
			return "You have no active bids\n";
		}
		
		String result = "Your Active Bids: \n";
		for (int i = 0; i < activeBids.size(); ++i) {
			result += activeBids.get(i).toStringActive() + "\n";
		}
		System.out.println();

		return result;
	}

	public String winningBidsToString() {

		if (winningBids.size() == 0) {
			return "You have no winning bids\n";
		}
		String result = "Winning Bids: \n";
		for (int i = 0; i < winningBids.size(); ++i) {
			result += winningBids.get(i).toStringWinning() + "\n";
		}

		return result;
	}

	public String usernameIdString() {
		return "Username: " + username + " UserID: " + userID;
	}

	public void addCurrentBid(Bid bid) {
		activeBids.add(bid);
	}

	public void removeActiveBid(Bid bid) {
		for(int i = 0; i < activeBids.size(); ++i) {
			if(activeBids.get(i).equals(bid)) {
				activeBids.remove(i);
			}
		}
		
	}

	public void addWinningBid(Bid bid) {
		winningBids.add(bid);
	}

	public void placeBid() {

	}

	public ArrayList<Bid> getActiveBids() {
		return activeBids;
	}

	public void setCurrentBids(ArrayList<Bid> activeBids) {
		this.activeBids = activeBids;
	}

	public ArrayList<Bid> getWinningBids() {
		return winningBids;
	}

	public void setWinningBids(ArrayList<Bid> winningBids) {
		this.winningBids = winningBids;
	}

}
