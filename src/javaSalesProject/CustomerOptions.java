package javaSalesProject;

public class CustomerOptions {
	
public static void customerMenu() {
		
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.customerMenu();
			
			//Check my active bids
			if (choice == 1) {
				System.out.println("You selected option 1");
			}
			
			//Check my winning bids
			
			else if (choice == 2) {
				System.out.println("You selected option 2");
			}
			
			//Bid on an item
			else if (choice == 3) {
				System.out.println("You selected option 3");
			}
			
			//Pay for an item that I won
			else if (choice == 4) {
				System.out.println("You selected option 4");
			}
			
			//Return to main menu
			else if (choice == 5) {
				System.out.println("You selected option 5");
				menu = false;
			}
		}
	}

}
