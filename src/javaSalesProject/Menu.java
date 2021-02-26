package javaSalesProject;

import java.util.Scanner;

public class Menu {

	
	//Main menu when there is no user logged in
	public static int mainMenuLoggedOut() {
		System.out.println("Main Menu\n" + 
				"=========\n" +
				"1. Load sample data (see information at *** below)\r\n" + 
				"2. Process the backlogged data (see information at *** below)\r\n" + 
				"3. Log in as administrator\r\n" + 
				"4. Log in as customer\r\n" + 
				"5. Exit the application\r\n");
		
		int choice = preventMismatchMainMenu();
		return choice;	
	}
	
	
	// Main menu when there is a user logged in
	public static int mainMenuLoggedIn() {
		
		if (Driver.currentUser.getUser() instanceof Admin) {
			
			System.out.println("Main Menu\n" +
				"=========\n" +
				"1. Load sample data (see information at *** below)\r\n" + 
				"2. Process the backlogged data (see information at *** below)\r\n" + 
				"3. Log out\r\n" +
				"4. Open admin menu\r\n" + 
				"5. Exit the application\r\n");
		}
		
		else if (Driver.currentUser.getUser() instanceof Customer) {
			
			System.out.println("Main Menu\n" +
					"=========\n" +
					"1. Load sample data (see information at *** below)\r\n" + 
					"2. Process the backlogged data (see information at *** below)\r\n" + 
					"3. Log out\r\n" +
					"4. Open customer menu\r\n" + 
					"5. Exit the application\r\n");
		}

		int choice = preventMismatchMainMenu();
		return choice;
	}
	
	
	public static int preventMismatchMainMenu() {
		int choice = 0;
		boolean allGood = false;
		while (allGood == false) {
			try {
				choice = validateMainMenu();
				allGood = true;
				return choice;
			}
			catch (Exception e) {
				System.out.println("Please make sure your input does not contain letters or special characters.");
			}
			
		}
		return choice;
	}
	
	
	public static int validateMainMenu() {
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		
		boolean valid = false;
		while (valid == false) {
			if (choice >= 1 && choice <= 5) {
				valid = true;
			}
			else {
				System.out.println("Please input a number between 1 and 5");
				choice = scan.nextInt();
			}
			
		}
		return choice;
	}

	
	public static void printCustomerMenu() {
		System.out.println("Customer Menu\n" +
				"=============\n" +
				"1. Check my active bids\r\n" + 
				"2. Check my winning bids\r\n" + 
				"3. Bid on an item\r\n" + 
				"4. Pay for an item that I won\r\n" + 
				"5. Return to the previous menu\r\n");
	}
	
	public static int customerMenu() {
		Scanner scan = new Scanner(System.in);
		
		int choice = 0;
		
		boolean valid = false;
		while (valid == false) {
			try {
				printCustomerMenu();
				System.out.print("Select an option from the menu: ");
				choice = scan.nextInt();
				
				if (choice >= 1 && choice <= 5) valid = true;
				else {
					System.out.println("Invalid input");
					System.out.println("Please select an option from the menu");
				}
			}
			
			catch (Exception e) {
				System.out.println("For your safety and mine, just input something from the menu");
				scan.nextLine();
			}	
		}
		return choice;
	}
	
	
	
	public static void printAdminMenu() {
		System.out.println("Admin Menu\n" +
				"==========\n" +
				"1. List current ongoing auctions\r\n" + 
				"2. Choose an ongoing auction and check the bidding history\r\n" + 
				"3. List information about completed auctions\r\n" + 
				"4. Summary data of winning bids\r\n" + 
				"5. Add and activate a new auction\r\n" + 
				"6. Return to the previous menu\r\n");
	}
	
	
	public static int adminMenu() {
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		
		boolean valid = false;
		while (valid == false) {
			try {
				printAdminMenu();
				System.out.print("Select an option from the menu: ");
				choice = scan.nextInt();
				
				if (choice >= 1 && choice <= 6) valid = true;
				else System.out.println("Please select an option from the menu");
			}
			
			catch (Exception e) {
				System.out.println("Please make sure your input is something from the menu");
				scan.nextLine();
			}	
		}
		return choice;
	}
	
	
	
	public void testingGeneralValidation(int lowerBound, int upperBound) {
		Scanner scan = new Scanner(System.in);
		
		int choice = 0;
		
		boolean valid = false;
		while (valid == false) {
		
			try {
				
				//This is an issue because this does not allow for a general method
				//printAdminMenu();
				
				
				System.out.print("Select an option from the menu: ");
				choice = scan.nextInt();
				
				//if (choice >= lowerBound && choice <= upperBound) valid = true;
				if (choice <= lowerBound || choice >= upperBound) {
					throw new InvalidInputException();
				}
				
				else System.out.println("Please select an option from the menu");
			}
			
			catch (InvalidInputException iie) {
				
			}
			
			catch (Exception e) {
				System.out.println("Please make sure your input is something from the menu");
				scan.nextLine();
			}	
		}
	}
	
	
	public static void printCustomerSubMenu() {
		System.out.println("Customer Sub Menu\n" +
				"=================\n" + 
				"1. Returning Customer\n" + 
				"2. New Customer\n" + 
				"3. Return to previous menu\n");
	}
	
	public static void printAdminSubMenu() {
		System.out.println("Admin Sub Menu\n" +
				"==============\n" + 
				"1. Returning Admin\n" + 
				"2. New Admin\n" + 
				"3. Return to previous menu\n");
	}
	
	public static int adminSubMenu() {
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		
		boolean valid = false;
		while (valid == false) {
			try {
				printAdminSubMenu();
				System.out.print("Select an option from the menu: ");
				choice = scan.nextInt();
				
				if (choice >= 1 && choice <= 3) valid = true;
				else System.out.println("Please select an option from the menu");
			}
			
			catch (Exception e) {
				System.out.println("Please make sure your input is something from the menu");
				scan.nextLine();
			}	
		}
		return choice;
	}
	
	
	public static int customerSubMenu() {
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		
		boolean valid = false;
		while (valid == false) {
			try {
				printCustomerSubMenu();
				System.out.print("Select an option from the menu: ");
				choice = scan.nextInt();
				
				if (choice >= 1 && choice <= 3) valid = true;
				else System.out.println("Please select an option from the menu");
			}
			
			catch (Exception e) {
				System.out.println("Please make sure your input is something from the menu");
				scan.nextLine();
			}	
		}
		return choice;
	}
	
}
