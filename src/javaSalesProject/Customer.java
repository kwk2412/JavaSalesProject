package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Account {

	private ArrayList<Bid> activeBids = new ArrayList<Bid>();
	private ArrayList<Bid> winningBids = new ArrayList<Bid>();
	private ArrayList<Bid> historicBids = new ArrayList<>();

	private double balance;

	public Customer() {
		super();
	}

	public Customer(String username, String password, String privileges) {
		super(username, password, privileges);
		//Driver.accounts.add(this);
	}

	// Constructor used when (re)creating accounts imported from a text file

	public Customer(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);
		this.balance = 0;
	}

	// Constructor used when (re)creating accounts imported from a text file
	public Customer(String username, String password, int userID, String privileges, double balance) {
		super(username, password, userID, privileges);
		this.balance = balance;
	}
	
	
	public String toString() {
		return "Username: " + username + "\n" +
				"Password: " + password + "\n" + 
				"Balance: " + balance + "\n";
	}

	
	public void printActiveBids() {
		if (activeBids.size() == 0) {
			System.out.println("You have placed no bids that are part of an ongoing auction");
		}
		else {
			System.out.println("Your Active Bids");
			System.out.println("=================");
			for (int i = 0; i < activeBids.size(); i++) {
				System.out.println(activeBids.get(i).toStringActive());
			}
		}
	}
	
	
	public void printWinningBids() {
		if (winningBids.size() == 0) {
			System.out.println("You have placed no bids that have won an auction");
		}
		else {
			System.out.println("Your Winning Bids");
			System.out.println("=================");
			for (int i = 0; i < winningBids.size(); i++) {
				System.out.println(winningBids.get(i).toStringWinning());
			}
		}
	}

	public String usernameIdString() {
		return "Username: " + username + " UserID: " + userID;
	}

	public void addCurrentBid(Bid bid) {
		activeBids.add(bid);
		historicBids.add(bid);
	}

	public void removeActiveBid(Bid bid) {
		for(int i = 0; i < activeBids.size(); ++i) {
			if(activeBids.get(i).equals(bid)) {
				activeBids.remove(i);
			}
		}
	}

	public void placeBid() {
		if (Driver.ongoingAuctions.size() != 0) {
			int auctionIndex = Menu.selectAuction();
			if (auctionIndex >= 0) {
				Auction auction = Driver.ongoingAuctions.get(auctionIndex);
				double value = InputMethods.getPositiveDouble("Enter the dollar amount of your bid");
				Bid b = new Bid(value, auction, this);				
				SystemMessage.print("Processing Bid: \n" + b.toString());
				if (Driver.isOpen()) {
					SystemMessage.print("Processing Bid: \n" + b.toString());
					auction.process(b);
					auction.printAuctionStatus();
				}
				else {
					SystemMessage.print("This bid will be processed when we open tomorrow: \n" + b.toString());
					auction.loadQueue(b);
				}
			}
		}
		else {
			System.out.println("There are no ongoing auctions");
		}
	}

	public void addWinningBid(Bid bid) {
		winningBids.add(bid);
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

	public ArrayList<Bid> getHistoricBids() {
		return historicBids;
	}

	public void setHistoricBids(ArrayList<Bid> historicBids) {
		this.historicBids = historicBids;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}	

}
