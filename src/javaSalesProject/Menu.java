package javaSalesProject;

import java.util.Scanner;

public class Menu {

	// Main menu when there is no user logged in
	public static int mainMenuLoggedOut() {
		String menu = "Main Menu\n" + "=========\n" + "1. Load sample data\r\n" + "2. Process the backlogged data\r\n"
				+ "3. Print items ArrayList\n" + "4. Log in as administrator\r\n" + "5. Log in as customer\r\n"
				+ "6. Exit the application\r\n";

		int choice = InputMethods.getIntFromMenu(1, 6, menu);
		return choice;
	}

	// Main menu when there is an admin is logged in
	public static int mainMenuAdminLoggedIn() {
		String menu = "Main Menu\n" + "=========\n" + "1. Load sample data\r\n" + "2. Process the backlogged data\r\n"
				+ "3. Print items ArrayList\n" + "4. Log out\r\n" + "5. Admin Options\r\n"
				+ "6. Exit the application\r\n";
		int choice = InputMethods.getIntFromMenu(1, 6, menu);
		return choice;
	}

	// Main menu for when there is a customer logged in
	public static int mainMenuCustLoggedIn() {

		String menu = "Main Menu\n" + "=========\n" + "1. Load sample data\r\n" + "2. Process the backlogged data\r\n"
				+ "3. Print items ArrayList\n" + "4. Log out\r\n" + "5. Open customer menu\r\n"
				+ "6. Exit the application\r\n";

		int choice = InputMethods.getIntFromMenu(1, 6, menu);
		return choice;
	}

	public static int customerMenu() {
		String menu = "Customer Menu\n" + "=============\n" + "1. Check my active bids\r\n"
				+ "2. Check my winning bids\r\n" + "3. Bid on an item\r\n" + "4. Pay for an item that I won\r\n"
				+ "5. Return to the previous menu\r\n";
		int choice = InputMethods.getIntFromMenu(1, 5, menu);
		return choice;
	}

	public static int adminMenu() {
		String menu = "Admin Menu\n" + "==========\n" + "1. List current ongoing auctions\r\n"
				+ "2. Choose an ongoing auction and check the bidding history\r\n"
				+ "3. List information about completed auctions\r\n" + "4. Summary data of winning bids\r\n"
				+ "5. Add and activate a new auction\r\n" + "6. Create new admin account\r\n"
				+ "7. Return to the previous menu\r\n";
		int choice = InputMethods.getIntFromMenu(1, 7, menu);
		return choice;
	}

	public static int customerSubMenu() {
		String menu = "Customer Sub Menu\n" + "=================\n" + "1. Returning Customer\n" + "2. New Customer\n"
				+ "3. Return to previous menu\n";
		int choice = InputMethods.getIntFromMenu(1, 3, menu);
		return choice;
	}

	// returns the idex of the item chosen
	public static int pickItemMenu() {
		if (Driver.items.size() > 0) {
			String menu = "Select the item to be sold:\n";
			for (int i = 0; i < Driver.items.size(); ++i) {
				menu += (i + 1) + ". " + Driver.items.get(i).toString() + "\n";
			}
			int choice = InputMethods.getIntOrReturnNeg1(1, Driver.items.size(), menu);
			if(choice > 0) {
				return choice - 1;
			}
		}
		return -1;
	}

	// returns the index of the ongoing auction.
	// returns -1 if the user backs out
	public static int selectAuction() {

		String menu = "Select an auction:\n";
		for (int i = 0; i < Driver.ongoingAuctions.size(); ++i) {
			menu += (i + 1) + ". " + Driver.ongoingAuctions.get(i).custVersionToString() + "\n";
		}
		int userSelection = InputMethods.getIntOrReturnNeg1(1, Driver.ongoingAuctions.size(), menu);
		if(userSelection > 0) {
			return (userSelection - 1);
		}
		return -1;
	}
}
