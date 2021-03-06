package javaSalesProject;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;



/**
 * This can read information from a text file and create objects that the program can manipulate.
 * The text file that this class reads in must meet a specific format. It must be divided into blocks
 * (separated by the } character), the first of which must contain all information for customer object
 * instantiation, the second must contain information for item instantiation, the third must contain
 * information for bid instantiation.
 * 
 * Within each block, the information that will be used to initialize the fields for each object must be
 * separated by pipes. The data that gets used to initialize the fields must be separated by commas. 
 * An example of such a text file may look like: 
 * 
 * Username, password, userID, balance, winningBids, activeBids, historicBids
 * Larry,password,3,7500,5#7,2#7#8#11#5,5#7#18#3#2#7#8#11#5|
 * Morton,password,4,6000,5#7,2#7#8#11#5,5#7#18#3#2#7#8#11#5|
 * Wendy,password,5,9000,5#7,2#7#8#11#5,5#7#18#3#2#7#8#11#5
 * }
 * 
 * startingPrice, name, increment, itemID
 * 130,Nintendo GameCube,10,100|
 * 160,Sony PlayStation,10,101|
 * 150,Nintendo GameBoy,5,102
 * }
 * 
 * auctionID,itemID,sellingPrice,currentHighest,processedBids,unprocessedBids
 * 1000,100,890,504,503#507#508,510#511#512|
 * 1003,101,710,518,523#525#526#527,528
 * 
 * auctionID,itemID,sellingPrice,winningBid,historicBids
 * 1002,102,650,518,513#514#515,516#517#518
 * }
 * 
 * bidValue,auctionID,customerID,bidID
 * 140,0,3,500|
 * 155,0,4,501|
 * 165,0,5,502
 * }
 * @author waveo
 *
 */
public class Read {
	
	// THIS MAIN METHOD WILL BE REMOVED AFTER THE READ CLASS HAS 
	// BEEN THOUROUGHLY TESTED AND RELIABLY WORKS
	public static void main(String[] args) {
		Driver.accounts = new ArrayList<Account>();
		Driver.items = new ArrayList<Item>();
		read();
	}
	
	public static void read() {

		ArrayList<String[]> blockArrays = new ArrayList<>();
		
		// 1. Convert the text file that you read in into a String
		//    then divide that string into blocks, persisting each block
		//	  as an element in an ArrayList
		
		String textFile = inputData();
			
		ArrayList<String> blocks = defineBlocks(textFile, "}");	
		
		// 2. Create empty arrays for each block from the text file, each array having
		//    a size specific to the number of objects that can be created given the 
		//	  information in the text file. These arrays are stored in an ArrayList
		//    that corresponds to the number of blocks that were found in the file.
		//    Each block has a corresponding array, the size of which depending on
		//    how many objects could be made based on what was found in the text file.
		
		// Creates an array for each block (customers, items, auctions, bids)
		for (int i = 0; i < blocks.size(); i++) {
			
			// Creates an array of equal size to the number of objects that can be created from the information
			// in that string
			blockArrays.add(new String[countDelimiters(blocks.get(i), '|') + 1]);
		}
		
		// 3. Fill those arrays with the information found in their corresponding blocks
		
		for (int i = 0; i < blockArrays.size(); i++) {
			fillArray(blockArrays.get(i), blocks.get(i));
		}
		
		// 4. Create objects based off of the information stored in the arrays
		//    and adds them to the rest of the program
		createObjects(blockArrays);
	}
	
	
	public static void createObjects(ArrayList<String[]> blockArrays) {
		// Creates items
		ArrayList<Item> items = new ArrayList<Item>();
		for (int i = 0; i < blockArrays.get(1).length; i++) {
			items.add(createItem(blockArrays.get(1)[i], items));
		}

		// Creates active auctions
		ArrayList<Auction> activeAuctions = new ArrayList<>();
		ArrayList<String[]> auctionBidBuffer = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(2).length; i++) {
			auctionBidBuffer.add(createActiveAuction(blockArrays.get(2)[i], items, activeAuctions));
		}

		// Creates completed auctions
		ArrayList<Auction> completedAuctions = new ArrayList<>();
		ArrayList<String> completedAuctionBuffer = new ArrayList<>();
		if (blockArrays.get(3)[0] != null) {
			for (int i = 0; i < blockArrays.get(3).length; i++) {
				completedAuctionBuffer.add(createCompletedAuction(blockArrays.get(3)[i], items, completedAuctions));
			}
		}

		// An ArrayList full of bid information that corresponds to the ArrayList of
		// customersAdded
		ArrayList<Customer> customersAdded = new ArrayList<>();
		ArrayList<String[]> customerBidBuffer = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(0).length; i++) {
			String[] bidInfo = createCustomer(blockArrays.get(0)[i], customersAdded);
			customerBidBuffer.add(bidInfo);
		}

		// Creates an ArrayList that stores all of the bids found in the text file
		ArrayList<Bid> bids = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(4).length; i++) {
			bids.add(createBid(blockArrays.get(4)[i], customersAdded, activeAuctions, completedAuctions));
		}
		
		populateCustomerBids(customersAdded, customerBidBuffer, bids);
		populateAuctionBids(activeAuctions, auctionBidBuffer, bids);
		populateCompletedAuctionBids(completedAuctionBuffer, completedAuctions, bids);
		addToDriver(items, activeAuctions, completedAuctions, customersAdded);
	}
	
	
	public static ArrayList<String> defineBlocks(String string, String delimiter) {
		ArrayList<String> substrings = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(string, delimiter);
		while (st.hasMoreTokens()) {
			substrings.add(st.nextToken().trim());
		}
		return substrings;
	}
	
	
	/*
	 * Takes in a string and a character and counts the number of times that
	 * character appears in the string
	 */
	public static int countDelimiters(String string, char delimiter) {
		int numDelimiters = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == delimiter) {
				numDelimiters++;
			}
		}
		return numDelimiters;
	}
	
	
	/*
	 * Fills an array that associated with a block with the information from the text file
	 * At this point, the arrays will be filled with Strings such as
	 * "Ronald,blue_plant" or "275,Test item 2,10"
	 */
	public static void fillArray(String[] array, String block) {
		StringTokenizer st = new StringTokenizer(block, "|");
		int counter = 0;
		while (st.hasMoreTokens()) {
			array[counter] = st.nextToken().trim();
			counter++;
		}
	}
	
	
	/*
	 * Takes in something like "Jake The Dog,Gr@yl!n3" and instantiates
	 * a customer object using the information separated by the comma
	 */
	public static String[] createCustomer(String data, ArrayList<Customer> customersAdded) {
		String[] info = toArray(data);
		
		String username = nullCheckString(info, 0);
		String password = nullCheckString(info, 1);
		int userID = nullCheckInteger(info, 2);
		double balance = nullCheckDouble(info, 3);
			
		Customer customer = new Customer(username, password, userID, "customer", balance);
		//Driver.accounts.add(customer);
		customersAdded.add(customer);
		
		// Return the bid IDs back to the ArrayList so we can load the customer's
		// bids once we load them into the program.
		String[] bidInfo = createBidInfo(info);
		return bidInfo;
	}
	
	
	public static String nullCheckString(String[] info, int index) {
		if (!info[index].equals("")) {
			return info[index];
		}
		else return null;
	}
	
	
	public static int nullCheckInteger(String[] info, int index) {
		if (!info[index].equals("")) {
			return Integer.parseInt(info[index]);
		}
		else return 0;
	}
	
	
	public static double nullCheckDouble(String[] info, int index) {
		if (!info[index].equals("")) {
			return Double.parseDouble(info[index]);
		}
		else return 0.0;
	}
	
	public static String[] createBidInfo(String[] info) {
		String[] bidInfo = new String[3];
		if(!info[4].equals("")) {
			bidInfo[0] = info[4];
		}
		else
			bidInfo[0] = "";
		if (!info[5].equals("")) {
			bidInfo[1] = info[5];
		}
		else
			bidInfo[1] = "";
		if (!info[6].equals("")) {
			bidInfo[2] = info[6];
		}
		else 
			bidInfo[2] = "";
		return bidInfo;
	}
	
	
	/*
	 * Takes in something like "200,GameBoy,5" and creates an Item
	 * object based on that information
	 */
	public static Item createItem(String data, ArrayList<Item> items) {
		String[] info = toArray(data);
		//Driver.items.add(new Item(Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2])));
		Item item = new Item(Double.parseDouble(info[0]), info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]));
		return item;
	}
	
	
	/*
	 * Creates an active auction from the data read in from the text file 
	 * 
	 * PLEASE REMOVE ALL ITEMS THAT WERE READ IN FROM THE MAIN ARRAYLIST OF ITEMS
	 * IF THOSE ITEMS ARE PART OF AN AUCTION. Otherwise, it will be displayed that we
	 * have more items available to do auctions on than we really do
	 */
	public static String[] createActiveAuction(String data, ArrayList<Item> items, ArrayList<Auction> auctions) {
		String[] info = toArray(data);
		Item item = findItem(Integer.parseInt(info[1]), items);
		double currentSellingPrice = nullCheckDouble(info, 2);
		int auctionID = nullCheckInteger(info, 0);
		
		Auction auction = new Auction(item, auctionID, currentSellingPrice);
		auctions.add(auction);
		
		String[] bidInfo = new String[2];
		if (info[4] != null)
			bidInfo[0] = info[4];
		else
			bidInfo[0] = null;
		if (info.length == 6 && info[5] != null)
			bidInfo[1] = info[5];
		else
			bidInfo[1] = null;
		
		return bidInfo;
	}
	
	 
	public static String createCompletedAuction(String data, ArrayList<Item> items, ArrayList<Auction> completedAuctions) {
		String[] info = toArray(data);
		Item item = findItem(Integer.parseInt(info[1]), items);
		Auction auction = new Auction(item, Integer.parseInt(info[0]));
		items.remove(item);
		auction.setActive(false);
		completedAuctions.add(auction);
		return info[4];
	}
	
	
	public static Bid createBid(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> auctionsAdded, ArrayList<Auction> completedAuctionsAdded) {
		String[] info = toArray(data);
		
		// info[1] contains the String version of the auctionID of the auction that the bid belongs to
		for (int i = 0; i < auctionsAdded.size(); i++) {
			if (auctionsAdded.get(i).getAuctionID() == Integer.parseInt(info[1])) {
				return createBidActive(data, customersAdded, auctionsAdded);
			}
		}
		return createBidCompleted(data, customersAdded, completedAuctionsAdded);
	}
	
	
	public static Bid createBidActive(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> auctionsAdded) {
		String[] info = toArray(data);
		Bid bid = new Bid(Double.parseDouble(info[0]), findAuction(Integer.parseInt(info[1]), auctionsAdded), findCustomer(Integer.parseInt(info[2]), customersAdded), Integer.parseInt(info[3]));
		return bid;
	}
	
	
	public static Bid createBidCompleted(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> completedAuctionsAdded) {
		String[] info = toArray(data);
		Bid bid = new Bid(Double.parseDouble(info[0]), findAuction(Integer.parseInt(info[1]), completedAuctionsAdded), findCustomer(Integer.parseInt(info[2]), customersAdded), Integer.parseInt(info[3]));
		return bid;
	}
	
	
	public static void populateCustomerBids(ArrayList<Customer> customers, ArrayList<String[]> bidBuffer, ArrayList<Bid> bids) {
		for (int i = 0; i < customers.size(); i++) {		
				
			String[] winningBidIDs = {};
			if (!bidBuffer.get(i)[0].equals("")) {
				winningBidIDs = bidBuffer.get(i)[0].split("#");
			}
			
			String[] activeBidIDs = {};
			if (!bidBuffer.get(i)[1].equals("")) {
				activeBidIDs = bidBuffer.get(i)[1].split("#");
			}
			
			String[] historicBidIDs = {};
			if (!bidBuffer.get(i)[2].equals("")) {
				historicBidIDs = bidBuffer.get(i)[2].split("#");
			}
				
			for (int j = 0; j < winningBidIDs.length; j++) {
				if (!winningBidIDs[0].equals("")) {
					customers.get(i).getWinningBids().add(findBid(Integer.parseInt(winningBidIDs[j]), bids));
				}
			}
			
			for (int j = 0; j < activeBidIDs.length; j++) {
				if (!activeBidIDs[0].equals("")) {
					customers.get(i).getActiveBids().add(findBid(Integer.parseInt(activeBidIDs[j]), bids));
				}
			}
			
			for (int j = 0; j < historicBidIDs.length; j++) {
				if (!historicBidIDs[0].equals("")) {
					customers.get(i).getHistoricBids().add(findBid(Integer.parseInt(historicBidIDs[j]), bids));
				}
			}
		}
	}
	
	
	public static void populateAuctionBids(ArrayList<Auction> auctions, ArrayList<String[]> auctionBidBuffer, ArrayList<Bid> bids) {
		for (int i = 0; i < auctions.size(); i++) {
			
			String[] processedBidIDs = {};
			if (auctionBidBuffer.get(i)[0] != null) {
				processedBidIDs = auctionBidBuffer.get(i)[0].split("#");
			}
			
			String[] unprocessedBidIDs = {};
			if (auctionBidBuffer.get(i)[1] != null) {
				unprocessedBidIDs = auctionBidBuffer.get(i)[1].split("#");
			}
			
			if (processedBidIDs.length != 0) {
				for (int j = 0; j < processedBidIDs.length; j++) {
					auctions.get(i).getProcessedBids().push(findBid(Integer.parseInt(processedBidIDs[j]), bids));
				}
			}
			
			if (unprocessedBidIDs.length != 0) {
				for (int j = 0; j < unprocessedBidIDs.length; j++) {
					auctions.get(i).getUnprocessedBids().enqueue(findBid(Integer.parseInt(unprocessedBidIDs[j]), bids));
				}
			}
			
			Bid highest = auctions.get(i).getProcessedBids().peek();
			auctions.get(i).setCurrentHighest(highest);
		}
	}
	
	public static void populateCompletedAuctionBids(ArrayList<String> historicBids, ArrayList<Auction> completedAuctions, ArrayList<Bid> bids) {
		for (int i = 0; i < historicBids.size(); i++) {
			String[] bidIDs = historicBids.get(i).split("#");
			
			for (int j = 0; j < bidIDs.length; j++) {
				completedAuctions.get(i).getProcessedBids().push(findBid(Integer.parseInt(bidIDs[j]), bids));
			}
		}
	}
	
	
	public static void addToDriver(ArrayList<Item> items, ArrayList<Auction> activeAuctions, ArrayList<Auction> completedAuctions, ArrayList<Customer> customersAdded) {
		for (int i = 0; i < items.size(); i++) {
			if (!items.isEmpty()) {
				Driver.items.add(items.get(i));
			}
		}
		
		for (int i = 0; i < activeAuctions.size(); i++) {
			if (!activeAuctions.isEmpty()) {
				Driver.ongoingAuctions.add(activeAuctions.get(i));
			}
		}
		
		for (int i = 0; i < completedAuctions.size(); i++) {
			if (!completedAuctions.isEmpty()) {
				Driver.completedAuctions.add(completedAuctions.get(i));
			}
		}
		
		for (int i = 0; i < customersAdded.size(); i++) {
			if (!customersAdded.isEmpty()) {
				Driver.accounts.add(customersAdded.get(i));
			}
		}
	}
	
	public static Customer findCustomer(int customerID, ArrayList<Customer> customers) {
		for (int i = 0; i < customers.size(); i++) {
			if (customerID == customers.get(i).getUserID()) {
				return customers.get(i);
			}
		}
		return null;
	}

	public static Auction findAuction(int auctionID, ArrayList<Auction> auctions) {
		for (int i = 0; i < auctions.size(); i++) {
			if (auctionID == auctions.get(i).getAuctionID()) {
				return auctions.get(i);
			}
		}
		return null;
	}

	public static Item findItem(int itemID, ArrayList<Item> items) {
		for (int i = 0; i < items.size(); i++) {
			if (itemID == items.get(i).getItemID()) {
				return items.get(i);
			}
		}
		return null;
	}

	// This method always returns null
	public static Bid findBid(int bidID, ArrayList<Bid> bids) {
		for (int i = 0; i < bids.size(); i++) {
			if (bidID == bids.get(i).getBidID()) {
				return bids.get(i);
			}
		}
		return null;
	}

	public static String[] toArray(String data) {
		String[] array = data.split(",");
		return array;
	}
	
	public static BufferedReader openRead() {
		Frame f = new Frame();
		// decide from where to read the file
		FileDialog foBox = new FileDialog(f, "Pick location for reading your file", FileDialog.LOAD);
		System.out.println("The dialog box will appear behind Eclipse.  " + 
		      "\n   Choose where you would like to read from.");
		foBox.setVisible(true);
		// get the absolute path to the file
		String foName = foBox.getFile();
		String dirPath = foBox.getDirectory();

		// create a file instance for the absolute path
		File inFile = new File(dirPath + foName);
		if (!inFile.exists()) {
			System.out.println("That file does not exist");
			System.exit(0);
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inFile));
		} catch (IOException e) {
			System.out.println("You threw an exception. ");
		}
		return in;
	}
	
	public static String inputData() {
		String concatLine = "";
		BufferedReader bf = null;
		try {
			// Open the file.
			bf = openRead();

			// read in the first line
			String line = "";
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// while there is more data in the file, process it
			while (line != null) {
				concatLine = concatLine + line + "\n";
				// read in the next line
				try {
					line = bf.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end of reading in the data.

		}
		// catch any other type of exception
		catch (Exception e) {
			System.out.println("Other weird things happened");
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (Exception e) {
			}
		}		
		return concatLine;
	}
	
}
