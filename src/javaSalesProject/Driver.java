package javaSalesProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.Timer;

public class Driver {


	private static Timer timer;
	private static boolean opening;
	static boolean running = false;
	static CurrentUser currentUser;
	static ArrayList<Account> accounts;
	static Admin rootUser;
	static ArrayList<Item> items; // Inventory of items to auction off
	static ArrayList<Auction> ongoingAuctions; // All active auctions
	static ArrayList<Auction> completedAuctions; // Auctions that have finished
	static ArrayList<Auction> futureAuctions;
	private static final LocalTime OPENTIME = LocalTime.of(9, 0);
	private static final LocalTime CLOSETIME = LocalTime.of(17, 0);

	public static void main(String[] args) {

		init();
		
		//Read.read();

		/*
		 * for (int i = 0; i < accounts.size(); i++) {
		 * System.out.println(accounts.get(i).toString()); }
		 * 
		 * Customer jake = new Customer("Jake", "password", "customer");
		 * 
		 * for (int i = 0; i < accounts.size(); i++) {
		 * System.out.println(accounts.get(i).toString()); }
		 * 
		 * Bid bid = new Bid(70, auction, jake);
		 */
		/*
		 * ArrayList<Bid> bids = new ArrayList<>();
		 * 
		 * bids.add(new Bid(60, auction, john)); bids.add(new Bid(80, auction, ralph));
		 * bids.add(new Bid(60, auction, billy)); bids.add(new Bid(70, auction, joel));
		 * 
		 * auction.getUnprocessedBids().enqueue(bids.get(0));
		 * auction.getUnprocessedBids().enqueue(bids.get(1));
		 * auction.getUnprocessedBids().enqueue(bids.get(2));
		 * auction.getUnprocessedBids().enqueue(bids.get(3));
		 * 
		 * for (int i = 0; i < bids.size(); i++) { auction.process(bids.get(i));
		 * System.out.println("Bid " + (i+1));
		 * System.out.println("Current Sales Price: " + auction.getCurrentSalesPrice());
		 * System.out.println("Current Highest Bid: " +
		 * auction.getCurrentHighest().getValue()); }
		 */

		while (running) {
			if (currentUser.getUser().getUserID() == 1)
				MainMenuOptions.menuNoUser();
			else if (currentUser.getUser() instanceof Admin)
				MainMenuOptions.menuAdminLoggedIn();
			else
				MainMenuOptions.menuCustomerLoggedIn();
		}
	}
/*
	public static void checkpointC() {
		Customer larry = new Customer("Larry", "password", "permissions");
		Customer morton = new Customer("Morton", "password", "permissions");
		Customer wendy = new Customer("Wendy", "password", "permissions");
		Customer iggy = new Customer("Iggy", "password", "permissions");
		Customer roy = new Customer("Roy", "password", "permissions");
		Customer lemmy = new Customer("Lemmy", "password", "permissions");
		Customer ludwig = new Customer("Ludwig", "password", "permissions");
<<<<<<< HEAD
		
=======

>>>>>>> branch 'Clay' of https://github.com/kwk2412/JavaSalesProject.git
		Auction auction = new Auction(items.get(0));
<<<<<<< HEAD
		
=======

>>>>>>> branch 'Clay' of https://github.com/kwk2412/JavaSalesProject.git
		System.out.println("Auction created for the first item in the inventory");
<<<<<<< HEAD
		
		Bid bid1 = new Bid(140, auction, larry);		
=======

		Bid bid1 = new Bid(140, auction, larry);
>>>>>>> branch 'Clay' of https://github.com/kwk2412/JavaSalesProject.git
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
<<<<<<< HEAD
		
=======

>>>>>>> branch 'Clay' of https://github.com/kwk2412/JavaSalesProject.git
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
<<<<<<< HEAD
=======

>>>>>>> branch 'Clay' of https://github.com/kwk2412/JavaSalesProject.git
	}
*/
	public static boolean loginAttemptCheck(boolean menu) {
		if (currentUser.getUser().userID != 1) {
			menu = false;
		} else {
			SystemMessage.print("Something went wrong with the login attempt. No user is currently logged in.");
		}
		return menu;
	}
	
	

	/*
	 * the purpose of this method is for setting the currentUser to the rootUser
	 * instance variable present in this class. The function of setting the
	 * currentUser equal to the rootUser has been given its own method for
	 * organization purposes. (When a user selects the log out option from the menu,
	 * we can call this method to set the currentUser back to rootUser)
	 * 
	 */
	public static void logout() {
		currentUser.setUser(rootUser);
	}

	
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
		for (int i = 0; i < 5; ++i) {
			LocalDateTime fakeStartTime = LocalDateTime.now();
			int seconds = fakeStartTime.getSecond();
			fakeStartTime = fakeStartTime.minusSeconds(seconds);
			LocalDateTime fakeEndTime = fakeStartTime.plusMinutes(i + 1);
			ongoingAuctions.add(new Auction(items.get(i), fakeStartTime, fakeEndTime));
		}
	}

	// All initial configurations to make the program run smoothly on startup
	// can happen in here, this cleans up the main method and keeps things organized
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
		futureAuctions = new ArrayList<Auction>();
		loadInventory();
		timer = new Timer(1000, null);
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkTime();

			}

		});
		timer.start();
		loadAuctions();
		
		if(isOpen()) {
			opening = true;
		} else {
			opening = false;
		}

		currentUser.setUser(new Admin("Clay", "p", "admin"));

	}

	// This method is called everytime the actionlistener hears the timer
	private static void checkTime() {
		checkForEndingAuctions();
		checkForStartingAuctions();
		if (isOpen() && opening) {
			checkUnprocessedBids();
			opening = false;
		}
		if(!isOpen()) {
			opening = true;
		}
	}

	private static void checkForEndingAuctions() {
		for (int i = 0; i < ongoingAuctions.size(); ++i) {
			if (ongoingAuctions.get(i).getEndDateTime().isBefore(LocalDateTime.now())) {
				ongoingAuctions.get(i).endAuction();
				--i;
			}
		}
	}

	private static void checkForStartingAuctions() {
		for (int i = 0; i < futureAuctions.size(); ++i) {
			if (futureAuctions.get(i).getStartDateTime().isBefore(LocalDateTime.now())) {
				Auction a = futureAuctions.remove(i);
				a.setActive(true);
				ongoingAuctions.add(a);
				--i;
			}
		}
	}

	private static void checkUnprocessedBids() {
		if (isOpen()) {
			for (int i = 0; i < ongoingAuctions.size(); ++i) {
				Auction a = ongoingAuctions.get(i);
				if (a.getUnprocessedBids().size() > 0) {
					a.automateAuction();
					--i;
				}
			}
		}
	}

	public static boolean isOpen() {
		if (LocalTime.now().isAfter(OPENTIME) && LocalTime.now().isBefore(CLOSETIME)) {
			return true;
		} else {
			return false;
		}
	}

}
