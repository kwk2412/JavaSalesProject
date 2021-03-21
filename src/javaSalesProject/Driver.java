package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	
	static boolean running = false;
	static CurrentUser currentUser;
	static ArrayList<Account> accounts;
	static Admin rootUser;
	static ArrayList<Item> items;	// Inventory of items to auction off
	static ArrayList<Auction> ongoingAuctions;	// All active auctions
	static ArrayList<Auction> completedAuctions;	// Auctions that have finished
		
	public static void main(String[] args) {
		
		init();	
		
		Item testItem = new Item(50, "Item 1", 5);
		Auction auction = new Auction(testItem);
		ongoingAuctions.add(auction);
		
		Item testItem2 = new Item(100, "testItem2", 10);
		Auction auctionTest2 = new Auction(testItem2);
		ongoingAuctions.add(auctionTest2);
		
		Customer john = new Customer("John", "password", "permissions");
		Customer ralph = new Customer("Ralph", "password", "permissions");
		Customer billy = new Customer("Billy", "password", "permissions");
		Customer joel = new Customer("Joel", "password", "persmissions");
		
		/*
		ArrayList<Bid> bids = new ArrayList<>();
		
		bids.add(new Bid(60, auction, john));
		bids.add(new Bid(80, auction, ralph));
		bids.add(new Bid(60, auction, billy));
		bids.add(new Bid(70, auction, joel));
		
		auction.getUnprocessedBids().enqueue(bids.get(0));
		auction.getUnprocessedBids().enqueue(bids.get(1));
		auction.getUnprocessedBids().enqueue(bids.get(2));
		auction.getUnprocessedBids().enqueue(bids.get(3));
		
		for (int i = 0; i < bids.size(); i++) {
			auction.process(bids.get(i));
			System.out.println("Bid " + (i+1));
			System.out.println("Current Sales Price: " + auction.getCurrentSalesPrice());
			System.out.println("Current Highest Bid: " + auction.getCurrentHighest().getValue());
		}
		*/

		while (running) {
			if (currentUser.getUser().getUserID() == 1) 
				MainMenuOptions.menuNoUser();
			else if(currentUser.getUser() instanceof Admin)
				MainMenuOptions.menuAdminLoggedIn();
			else
				MainMenuOptions.menuCustomerLoggedIn();
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
		for(int i = 0; i < 5; ++i) {
			ongoingAuctions.add(new Auction(items.get(i)));
		}
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
		ongoingAuctions = new ArrayList<Auction>();
		completedAuctions = new ArrayList<Auction>();
		loadSampleItemsIntoInventory();
		//loadAuctions();
		currentUser.setUser(new Customer("Clay", "password", "customer"));
	}
	
}

