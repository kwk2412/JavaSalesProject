package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	
	static boolean running = false;
	static CurrentUser currentUser;
	static ArrayList<Account> accounts;
	static Admin rootUser;
	static ArrayList<Item> items;
		
	public static void main(String[] args) {
	
		init();	

		while (running) {
			if (currentUser.getUser().getUserID() == 1) menuNoUser();
			else menuUserLoggedIn();
		}		

	}
	
	
	public static void menuNoUser() {
		Scanner scan = new Scanner(System.in);
		
		AccountValidation av = new AccountValidation();
		
		boolean menu = true;
		while (menu) {

			int menuChoice = Menu.mainMenuLoggedOut();

			if (menuChoice == 1) {
				System.out.println("you selected 1, this will load the sample data when we get that figured out\n");
			}
			
			else if (menuChoice == 2) {
				System.out.println("you selected 2, this will process backlogged data when we know what that means\n");
				processBackloggedData();
			}
			
			else if (menuChoice == 3) {
				adminSubMenu();
				menu = false;
			}
			
			else if (menuChoice == 4) {
				customerSubMenu();
				menu = false;
			}
			
			else if (menuChoice == 5) {
				menu = false;
				System.out.println("Ending program...");
				System.exit(0);
			}
		}
	}
	
	
	public static void menuUserLoggedIn() { 
		Scanner scan = new Scanner(System.in);
		
		boolean menu = true;
		while (menu) {

			//This method handles printing the appropriate version of the menu
			//depending on whether the user is a customer or admin
			int menuChoice = Menu.mainMenuLoggedIn();
			
			//Load sample data
			if (menuChoice == 1) {
				System.out.println("you selected 1, this will load the sample data when we get that figured out\n");
			}
			
			//Process backlogged data
			else if (menuChoice == 2) {
				System.out.println("you selected 2, this will process backlogged data when we know what that means\n");
				processBackloggedData();
			}
			
			//log out
			else if (menuChoice == 3) {		
				System.out.println("Log out and return to main menu?");
				boolean answer = InputMethods.yesNoToBool("Log out and return to main menu?");
				
				if (answer) {
					logout();
					menu = false;
				}
			}
			
			//Continue as customer/admin
			else if (menuChoice == 4) {
				if (currentUser.getUser() instanceof Admin) AdminOptions.adminMenu();
				else if (currentUser.getUser() instanceof Customer) CustomerOptions.customerMenu();
			}

			//Exit the application
			else if (menuChoice == 5) {
				menu = false;
				System.out.println("Ending program...");
				System.exit(0);
			}
		}
	}
	
	
	public static void customerSubMenu() {
		
		AccountValidation av = new AccountValidation();
		
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.customerSubMenu();
			
			//Returning customer
			if (choice == 1) {
				av.login("customer");
				menu = loginAttemptCheck(menu);
			}
			
			//New customer
			else if (choice == 2) {
				CreateAccount.createCustomerAccount();
				menu = loginAttemptCheck(menu);
			}
			
			//Return to the previous menu
			else if (choice == 3) {
				menu = false;
			}
		}
	}
	
	public static void adminSubMenu() {
		
		AccountValidation av = new AccountValidation();
		
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.adminSubMenu();
			
			//Returning Admin
			if (choice == 1) {
				av.login("admin");
				menu = loginAttemptCheck(menu);
			}
			
			//New Admin
			else if (choice == 2) {
				CreateAccount.createAdminAccount();
				menu = loginAttemptCheck(menu);
			}
			
			//Return to previous menu
			else if (choice == 3) {
				menu = false;
			}
		}
	}
	
	public static boolean loginAttemptCheck(boolean menu) {
		if (currentUser.getUser().userID != 1) {
			menu = false;
		}
		else {
			System.out.println("Something went wrong with the login attempt. No user is currently logged in.");
		}
		return menu;
	}
	
	
	
	/*
	 * the purpose of this method is for setting the currentUser to 
	 * the rootUser instance variable present in this class.
	 * The function of setting the currentUser equal to the rootUser
	 * has been given its own method for organization purposes.
	 * (When a user selects the log out option from the menu,
	 * we can call this method to set the currentUser back
	 * to rootUser)
	 * 
	 */
	public static void logout() {
		currentUser.setUser(rootUser);
	}
	
	//stub method that may be used to govern the functionality of actions associated
	//with processing backlogged data
	public static void processBackloggedData() {
		
	}
	
	
	//All initial configurations to make the program run smoothly on startup
	//can happen in here, this cleans up the main method and keeps things organized
	public static void init() {
		running = true;
		accounts = new ArrayList<Account>();
		currentUser = new CurrentUser();
		rootUser = new Admin("rootUser", "password", "admin");		
		currentUser.setUser(rootUser);
	}
	
	//The menu for the code that Clay wrote in the early phases of the program's creation
	//Created for the purpose of demonstrating how these methods would be called and used
	public static void menuClay() {
		
		Scanner keyboard = new Scanner(System.in);
		boolean done = false;
		while(!done) {
			System.out.println("1. create customer account. 2. Create admin account. 3. print all accounts. 4. Show current user"
					+ " 5. Input accounts to text file. 6. Output accounts to a text file.");
			int selection = keyboard.nextInt();
			if(selection == 1) {
				CreateAccount.createCustomerAccount();
			} else if(selection == 2) {
				CreateAccount.createAdminAccount();
			} else if(selection == 3) {
				Testing.printAllAccounts();
			} else if(selection == 4) {
				System.out.println(currentUser.toString());
			} else if(selection == 5) {
				ReadWriteAccounts.inputAccounts();
			} else if(selection == 6) {
				ReadWriteAccounts.outputAccounts();	
			} else
				done = true;
		}
	}
	
}
