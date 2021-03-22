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

		while (running) {
			if (currentUser.getUser().getUserID() == 1) 
				MainMenuOptions.menuNoUser();
			else if(currentUser.getUser() instanceof Admin)
				MainMenuOptions.menuAdminLoggedIn();
			else
				MainMenuOptions.menuCustomerLoggedIn();
		}		
	}
	
	
	public static void checkpointC() {
		Customer larry = new Customer("Larry", "password", "permissions");
		Customer morton = new Customer("Morton", "password", "permissions");
		Customer wendy = new Customer("Wendy", "password", "permissions");
		Customer iggy = new Customer("Iggy", "password", "permissions");
		Customer roy = new Customer("Roy", "password", "permissions");
		Customer lemmy = new Customer("Lemmy", "password", "permissions");
		Customer ludwig = new Customer("Ludwig", "password", "permissions");
		
		Auction auction = new Auction(items.get(0));
		
		System.out.println("Auction created for the first item in the inventory");
		
		Bid bid1 = new Bid(140, auction, larry);		
		Bid bid2 = new Bid(155, auction, morton);
		Bid bid3 = new Bid(165, auction, wendy);
		Bid bid4 = new Bid(160, auction, iggy);
		Bid bid5 = new Bid(180, auction, iggy);
		Bid bid6 = new Bid(210, auction, roy);
		Bid bid7 = new Bid(230, auction, lemmy);
		Bid bid8 = new Bid(220, auction, ludwig);
		Bid bid9 = new Bid(245, auction, iggy);
		Bid bid10 = new Bid(250, auction, morton);
		Bid bid11 = new Bid(260, auction, ludwig);
		Bid bid12 = new Bid(270, auction, roy);
		Bid bid13 = new Bid(300, auction, ludwig);
		
		auction.getUnprocessedBids().enqueue(bid1);
		auction.getUnprocessedBids().enqueue(bid2);
		auction.getUnprocessedBids().enqueue(bid3);
		auction.getUnprocessedBids().enqueue(bid4);
		auction.getUnprocessedBids().enqueue(bid5);
		auction.getUnprocessedBids().enqueue(bid6);
		auction.getUnprocessedBids().enqueue(bid7);
		auction.getUnprocessedBids().enqueue(bid8);
		auction.getUnprocessedBids().enqueue(bid9);
		auction.getUnprocessedBids().enqueue(bid10);
		auction.getUnprocessedBids().enqueue(bid11);
		auction.getUnprocessedBids().enqueue(bid12);
		auction.getUnprocessedBids().enqueue(bid13);
		
		System.out.println();
		
		auction.automateAuction();
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

	// Stub method that may be used to govern the functionality of actions associated
	// with processing backlogged data
	public static void processBackloggedData() {
		
	}
	
	
	public static void printItems() {
		if (items.size() == 0) 
			SystemMessage.print("Items ArrayList is empty");
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).toString());
		}
	}

	public static void loadInventory() {
		items.add(new Item(130, "Nintendo GameCube", 10));
		items.add(new Item(160, "Sony PlayStation", 10));
		items.add(new Item(150, "Nintendo GameBoy", 5));
		items.add(new Item(170, "Microsoft Xbox", 10));
		items.add(new Item(125, "Nintendo 64", 5));
		items.add(new Item(180, "Sony PlayStation 2", 5));
		items.add(new Item(90, "Sega Dreamcast", 10));
		items.add(new Item(190, "Sony PlayStation Portable", 10));
		items.add(new Item(230, "Microsoft Xbox 360", 10));
		items.add(new Item(80, "Atari 2600", 15));
		items.add(new Item(120, "Sega CD", 10));
		items.add(new Item(90, "Magnavox Odyssey", 15));
		items.add(new Item(250, "Nintendo GameCube", 10));
		items.add(new Item(180, "Nintendo Virtual Boy", 20));
		items.add(new Item(80, "Nintendo Entertainment System", 10));
		items.add(new Item(200, "Sony PlayStation 3", 15));
		items.add(new Item(130, "Sega GameGear", 10));
		items.add(new Item(250, "Microsoft Xbox One", 15));
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
		loadInventory();
		//loadAuctions();
		//currentUser.setUser(new Customer("Clay", "password", "customer"));
	}
	
}

