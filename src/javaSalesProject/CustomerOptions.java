package javaSalesProject;

import java.util.Scanner;

public class CustomerOptions {
	
	public static void customerMenu() {
		Customer c = (Customer) Driver.currentUser.getUser();
		boolean menu = true;
		Scanner scan = new Scanner(System.in);
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
			
			//Add money to balance
			else if (choice == 4) {
				System.out.println("How much money would you like to add to your balance?");
				double moneyToBeAdded = scan.nextDouble();				
				c.setBalance(c.getBalance()+moneyToBeAdded);
			}
			
			//Pay for an item that I won
			else if (choice == 5) {
				System.out.println("You selected option 4");
			}
			
			//Return to main menu
			else if (choice == 6) {
				menu = false;
			}
		}
	}

}
