package javaSalesProject;

import java.util.Scanner;

public class Admin extends Account{
	
	public Admin() {
		super();
	}
	
	public Admin(String username, String password, String privileges) {
		super(username, password, privileges);
	}
	
	// Constructor used when (re)creating accounts imported from a text file
	public Admin(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);
		
	}
	
	public void startNewAuction() {
		if (Driver.items.size() > 0) {
			Item toBeSold = getItemToBeSold();
			if (toBeSold != null) {
				Auction a = new Auction(toBeSold);
				Driver.ongoingAuctions.add(a);
				SystemMessage.print("Auction Added:\n" + a.toString());
			}
		} else {
			System.out.println("The inventory is empty");
		}
	}
	
	private Item getItemToBeSold() {
		int indexOfItem = Menu.pickItemMenu();
		if (indexOfItem == -1) {
			return null;
		} else
			return Driver.items.get(indexOfItem);
	}
	
	public void showOngoingAuctions() {
		if (Driver.ongoingAuctions.size() == 0) {
			System.out.println("There are no ongoing auctions");
		}
		else {
			System.out.println("Ongoing Auctions");
			System.out.println("=================");
			for (int i = 0; i < Driver.ongoingAuctions.size(); i++) {
				System.out.println(Driver.ongoingAuctions.get(i).toString());
			}
		}
	}
	
}
