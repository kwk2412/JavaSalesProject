package javaSalesProject;

public class CustomerOptions {
	
	public static void customerMenu() {
		Customer c = (Customer) Driver.currentUser.getUser();
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.customerMenu();
			
			// Check my active bids
			if (choice == 1) {
				c.printActiveBids();
			}
			
			//Check my winning bids
			else if (choice == 2) {
				c.printWinningBids();
			}
			
			//Bid on an item
			else if (choice == 3) {
				c.placeBid();
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
