package javaSalesProject;

public class CustomerOptions {
	
public static void customerMenu() {
		
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.customerMenu();
			
			if (choice == 1) {
				// Check my active bids
				Customer c = (Customer) Driver.currentUser.getUser();
				System.out.println(c.activeBidsToString());
			}
			
			//Check my winning bids
			else if (choice == 2) {
				Customer c = (Customer) Driver.currentUser.getUser();
				System.out.println(c.winningBidsToString());
			}
			
			//Bid on an item
			else if (choice == 3) {
				AuctionMethods.bidOnAnItem();
			}
			
			//Pay for an item that I won
			else if (choice == 4) {
				System.out.println("You selected option 4");
			}
			
			//Return to main menu
			else if (choice == 5) {
				menu = false;
			}
		}
	}

}
