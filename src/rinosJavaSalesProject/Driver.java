package rinosJavaSalesProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.Timer;

public class Driver {

	private static Timer timer;
	private static boolean opening;
	static boolean running = false;
	static boolean invLoaded = false;
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

		while (running) {
			if (currentUser.getUser().getUserID() == 1)
				MainMenuOptions.menuNoUser();
			else if (currentUser.getUser() instanceof Admin)
				MainMenuOptions.menuAdminLoggedIn();
			else
				MainMenuOptions.menuCustomerLoggedIn();
		}
	}

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
	
	public static void printItems() {
		if (items.size() == 0)
			SystemMessage.print("Items ArrayList is empty");
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).toString());
		}
	}

	public static void loadInventory() {
		if (!invLoaded) {
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
			
			System.out.println("The data has been loaded into the ArrayList");
		}
		else {
			System.out.println("The data is already loaded");
		}
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
		//loadInventory();
		invLoaded = true;
		timer();
		currentUser.setUser(new Admin("Clay", "p", "admin"));
	}
	
	/**
	 * Creates and starts a timer that goes off every second.
	 * Listens for the timer to go off. 
	 * When it hears the timer go off it calls checkTime()
	 * 
	 */
	public static void timer() {
		timer = new Timer(1000, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTime();
			}
		});
		
		timer.start();

		if (isOpen()) {
			opening = true;
		}
		else {
			opening = false;
		}
	}

	/**
	 * This method is called every second by timer()
	 * It calls checkForEndingAuctions() and checkForStartingAuctions() to check for starting and ending auctions
	 * 
	 * The first time this method is called after the site opens it will
	 * call checkUnprocessedBids() to handle any bids were placed after hours.
	 * 
	 */
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

	/*
	 * Called once every second by checkTime().
	 * Looks through all of the ongoing auctions to see if it is time to end them.
	 * Calls endAuction() on any auction with an end time in the past.
	 */
	private static void checkForEndingAuctions() {
		for (int i = 0; i < ongoingAuctions.size(); ++i) {
			if (ongoingAuctions.get(i).getEndDateTime().isBefore(LocalDateTime.now())) {
				ongoingAuctions.get(i).endAuction();
				--i;
			}
		}
	}
	
	/*
	 * Called once every second by checkTime().
	 * Looks through all of the future auctions to see if it is time to start them.
	 * Any future auction with a start time in the past will be set active and moved to ongoing auctions.
	 */
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
	
	/*
	 * Called once a day the first second that the site is open
	 * Goes though all of the ongoing auction to see if any bids were placed after hours
	 * If there were any bids placed after hours will call automateAuction() to process them.
	 */
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

	/*
	 * Used to determine if the site is currently open
	 * @return A boolean that is true if the site is open and false if the site is closed.
	 */
	public static boolean isOpen() {
		if (LocalTime.now().isAfter(OPENTIME) && LocalTime.now().isBefore(CLOSETIME)) {
			return true;
		}
		else {
			return false;
		}
	}

}
