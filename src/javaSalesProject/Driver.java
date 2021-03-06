package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	
	static boolean running = false;
	static CurrentUser currentUser;
	static ArrayList<Account> accounts;
	static Admin rootUser;
	static ArrayList<Item> items;
	static ArrayList<Auction> auctions;
		
	public static void main(String[] args) {
		
		init();	

		while (running) {
			if (currentUser.getUser().getUserID() == 1) 
				menuNoUser();
			else if(currentUser.getUser() instanceof Admin)
				menuAdminLoggedIn();
			else
				menuCustomerLoggedIn();
		}		

	}
	
	
	public static void menuNoUser() {
		Scanner scan = new Scanner(System.in);
		
		AccountValidation av = new AccountValidation();
		
		boolean menu = true;
		while (menu) {

			int menuChoice = Menu.mainMenuLoggedOut();

			if (menuChoice == 1) {
				loadSampleItemsIntoInventory();
				SystemMessage.print("Data has been loaded into the ArrayList");
			}
			
			else if (menuChoice == 2) {
				System.out.println("you selected 2, this will process backlogged data when we know what that means\n");
				processBackloggedData();
			}
			
			else if (menuChoice == 3) {
				printItems();
			}
			
			else if (menuChoice == 4) {
				av.login("admin");
				menu = false;
			}
			
			else if (menuChoice == 5) {
				customerSubMenu();
				menu = false;
			}
			
			else if (menuChoice == 6) {
				boolean sure = InputMethods.yesNoToBool("Are you sure you want to quit?");
				if (sure) {
					System.out.println("Ending program...");
					System.exit(0);
				}
			}
		}
	}
	
	
	public static void menuAdminLoggedIn() { 
		Scanner scan = new Scanner(System.in);
		
		boolean menu = true;
		while (menu) {

			//This method handles printing the appropriate version of the menu
			//depending on whether the user is a customer or admin
			int menuChoice = Menu.mainMenuAdminLoggedIn();
			
			//Load sample data
			if (menuChoice == 1) {
				loadSampleItemsIntoInventory();
				SystemMessage.print("Data has been loaded into the ArrayList");
			}
			
			//Process backlogged data
			else if (menuChoice == 2) {
				System.out.println("you selected 2, this will process backlogged data when we know what that means\n");
				processBackloggedData();
			}
			
			// Load item data
			else if (menuChoice == 3) {
				printItems();
			}
			
			//log out
			else if (menuChoice == 4) {		
				boolean answer = InputMethods.yesNoToBool("Log out and return to main menu? (yes or no)");
				
				if (answer) {
					logout();
					menu = false;
				}
			}
			
			//Continue as admin
			else if (menuChoice == 5) {
				AdminOptions.adminMenu();

			}

			//Exit the application
			else if (menuChoice == 6) {
				boolean sure = InputMethods.yesNoToBool("Are you sure you want to quit? (yes or no)");
				if (sure) {
					System.out.println("Ending program...");
					System.exit(0);
				}
			}
		}
	}
	
	
	public static void menuCustomerLoggedIn() { 
		Scanner scan = new Scanner(System.in);
		
		boolean menu = true;
		while (menu) {

			//This method handles printing the appropriate version of the menu
			//depending on whether the user is a customer or admin
			int menuChoice = Menu.mainMenuCustLoggedIn();
			
			//Load sample data
			if (menuChoice == 1) {
				loadSampleItemsIntoInventory();
				SystemMessage.print("Data has been loaded into the ArrayList");
			}
			
			//Process backlogged data
			else if (menuChoice == 2) {
				System.out.println("you selected 2, this will process backlogged data when we know what that means\n");
				processBackloggedData();
			}
			
			// Load item data
			else if (menuChoice == 3) {
				printItems();
			}
			
			//log out
			else if (menuChoice == 4) {		
				boolean answer = InputMethods.yesNoToBool("Log out and return to main menu? (yes or no)");
				
				if (answer) {
					logout();
					menu = false;
				}
			}
			
			//Continue as customer
			else if (menuChoice == 5) {
				CustomerOptions.customerMenu();

			}

			//Exit the application
			else if (menuChoice == 6) {
				boolean sure = InputMethods.yesNoToBool("Are you sure you want to quit? (yes or no)");
				if (sure) {
					System.out.println("Ending program...");
					System.exit(0);
				}
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
	
	public static boolean loginAttemptCheck(boolean menu) {
		if (currentUser.getUser().userID != 1) {
			menu = false;
		}
		else {
			SystemMessage.print("Something went wrong with the login attempt. No user is currently logged in.");
		}
		return menu;
	}
	
	public static void printItems() {
		if (items.size() == 0) 
			SystemMessage.print("Items ArrayList is empty");
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).toString());
		}
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
	
	
	public static void loadSampleItemsIntoInventory() {
		items.add(new Item(130, "Nintendo GameCube", 10));
		items.add(new Item(160, "Sony PlayStation", 10));
		items.add(new Item(150, "Nintendo GameBoy", 5));
		items.add(new Item(170, "Microsoft XboX", 10));
		items.add(new Item(125, "Nintendo 64", 5));
		items.add(new Item(180, "Sony PlayStation 2", 5));
		items.add(new Item(90, "Sega Dreamcast", 10));
		items.add(new Item(190, "Sony PlayStation Portable", 10));
		items.add(new Item(230, "Microsoft XboX 360", 10));
		items.add(new Item(80, "Atari 2600", 15));
		items.add(new Item(120, "Sega CD", 10));
		items.add(new Item(90, "Magnavox Odyssey", 15));
		items.add(new Item(250, "Nintendo GameCube", 10));
		items.add(new Item(180, "Nintendo Virtual Boy", 20));
		items.add(new Item(80, "Nintendo Entertainment System", 10));
		items.add(new Item(200, "Sony PlayStation 3", 15));
		items.add(new Item(130, "Sega GameGear", 10));
		items.add(new Item(250, "Microsoft XboX One", 15));
	}
	
	public static void loadAuctions() {
		//auctions.add(new Auction("Lenovo"));
		//auctions.add(new Auction("Asus"));
	}
	
	
	//All initial configurations to make the program run smoothly on startup
	//can happen in here, this cleans up the main method and keeps things organized
	public static void init() {
		running = true;
		accounts = new ArrayList<Account>();
		currentUser = new CurrentUser();
		rootUser = new Admin("rootUser", "password", "admin");	
		new Admin("Admin", "password", "admin");
		currentUser.setUser(rootUser);
		items = new ArrayList<Item>();
		auctions = new ArrayList<Auction>();
	}
	
}

