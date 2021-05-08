package rinosJavaSalesProject;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
 * startingPrice,name,increment,itemID,isPaidFor,isAvailable
 * 130,Nintendo GameCube,10,100,0,1|
 * 160,Sony PlayStation,10,101,0,0|
 * 150,Nintendo GameBoy,5,102,1,1
 * }
 * 
 * auctionID,itemID,sellingPrice,currentHighest,processedBids,unprocessedBids,openingTime*,closingTime*
 * 1000,100,890,504,503#507#508,510#511#512,2020#4#8#9#0#0,2020#4#15#17#0#0|
 * 1003,101,710,518,523#525#526#527,528,2020#4#11#9#0#0,2020#4#13#17#0#0
 * 
 * auctionID,itemID,sellingPrice,winningBid,historicBids,openingTime,closingTime
 * 1002,102,650,518,513#514#515,516#517#518,
 * }
 * 
 * auctionID, itemID, openingTime, closingTime
 * 1004,103,2020#4#9#12#0#0,2020#4#9#14#0#0
 * }
 * 
 * bidValue,auctionID,customerID,bidID,year,month,day,hour,minute,second
 * 140,0,3,500,2020#4#8#11#0#0|
 * 155,0,4,501,2020#4#8#11#2#0|
 * 165,0,5,502,2020#4#8#11#3#0
 * 
 * **A $ indicates that the auction being read in is a dynamic auction used for testing purposes
 * These auctions are set to close 5 minutes after opening when placed in the active auctions block,
 * and when they are placed in the future auctions block they are scheduled to open 2 minutes after
 * they get read in and close five minutes after they open.
 * @author waveo
 */
public class Read {
	
	// THIS MAIN METHOD WILL BE REMOVED AFTER THE READ CLASS HAS 
	// BEEN THOUROUGHLY TESTED AND RELIABLY WORKS
	public static void main(String[] args) {
		Driver.accounts = new ArrayList<Account>();
		Driver.ongoingAuctions = new ArrayList<Auction>();
		Driver.completedAuctions = new ArrayList<Auction>();
		Driver.futureAuctions = new ArrayList<Auction>();
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
		
		System.out.println("No errors in the importing process");
	}
	
	
	public static void createObjects(ArrayList<String[]> blockArrays) {
		// Creates items
		ArrayList<Item> items = new ArrayList<Item>();
		for (int i = 0; i < blockArrays.get(1).length; i++) {
			if (blockArrays.get(1)[0] != null) {
				items.add(createItem(blockArrays.get(1)[i], items));
			}
			
		}

		// Creates active auctions
		ArrayList<Auction> activeAuctions = new ArrayList<>();
		ArrayList<String[]> auctionBidBuffer = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(2).length; i++) {
			if  (blockArrays.get(2)[0] != null) {
				auctionBidBuffer.add(createActiveAuction(blockArrays.get(2)[i], items, activeAuctions));
			}
		}

		// Creates completed auctions
		ArrayList<Auction> completedAuctions = new ArrayList<>();
		ArrayList<String> completedAuctionBuffer = new ArrayList<>();
		if (blockArrays.get(3)[0] != null) {
			for (int i = 0; i < blockArrays.get(3).length; i++) {
				if (blockArrays.get(3).length != 0) {
					completedAuctionBuffer.add(createCompletedAuction(blockArrays.get(3)[i], items, completedAuctions));
				}
			}
		}
		
		//Creates future auctions
		ArrayList<Auction> futureAuctions = new ArrayList<>();
		if (blockArrays.get(4)[0] != null) {
			for (int i = 0; i < blockArrays.get(4).length; i++) {
				if (blockArrays.get(4).length != 0) {
					futureAuctions.add(createFutureAuction(blockArrays.get(4)[i], items));
				}
			}
		}

		// An ArrayList full of bid information that corresponds to the ArrayList of
		// customersAdded
		ArrayList<Customer> customersAdded = new ArrayList<>();
		ArrayList<String[]> customerBidBuffer = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(0).length; i++) {
			if (blockArrays.get(0)[0] != null) {
				String[] bidInfo = createCustomer(blockArrays.get(0)[i], customersAdded);
				customerBidBuffer.add(bidInfo);
			}
			
		}

		// Creates an ArrayList that stores all of the bids found in the text file
		ArrayList<Bid> bids = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(5).length; i++) {
			if (blockArrays.get(5)[0] != null) {
				bids.add(createBid(blockArrays.get(5)[i], customersAdded, activeAuctions, completedAuctions));
			}
		}
		
		populateCustomerBids(customersAdded, customerBidBuffer, bids);
		populateAuctionBids(activeAuctions, auctionBidBuffer, bids);
		populateCompletedAuctionBids(completedAuctionBuffer, completedAuctions, bids);
		addToDriver(items, activeAuctions, completedAuctions, futureAuctions, customersAdded);
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

	public static String[] createBidInfo(String[] info) {
		String[] bidInfo = new String[3];
		
		if (info.length > 4) {
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
		}
		
		return bidInfo;
	}
	
	
	/*
	 * Takes in something like "200,GameBoy,5" and creates an Item
	 * object based on that information
	 */
	public static Item createItem(String data, ArrayList<Item> items) {
		String[] info = toArray(data);
		boolean paidFor = false;
		int paidForFlag = nullCheckInteger(info, 4);
		if (paidForFlag == 1) paidFor = true;
		boolean available = false;
		int availableFlag = nullCheckInteger(info, 5);
		if (availableFlag == 1) available = true;
		//Driver.items.add(new Item(Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2])));
		Item item = new Item(Double.parseDouble(info[0]), info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), paidFor, available);
		return item;
	}
	
	
	/**
	 * Creates an active auction from the data read in from the text file
	 */
	public static String[] createActiveAuction(String data, ArrayList<Item> items, ArrayList<Auction> auctions) {
		String[] info = toArray(data);
		
		int auctionID = nullCheckInteger(info, 0);
		Item item = findItem(Integer.parseInt(info[1]), items);
		double currentSellingPrice = nullCheckDouble(info, 2);
		LocalDateTime openingTime;
		LocalDateTime closingTime;
		Auction auction;		
		if (info[6].equals("$")) {
			openingTime = LocalDateTime.now();
			closingTime = openingTime.plusMinutes(5);
		}
		else {
			openingTime = createDateTime(info[6]);
			closingTime = createDateTime(info[7]);
		}
		auction = new Auction(item, auctionID, currentSellingPrice, openingTime, closingTime);
		auctions.add(auction);
		
		String[] bidInfo = new String[2];
		if (info[4] != null)
			bidInfo[0] = info[4];
		else
			bidInfo[0] = null;
		if (info.length >= 6 && info[5] != null)
			bidInfo[1] = info[5];
		else
			bidInfo[1] = null;
		
		return bidInfo;
	}
	
	 
	public static String createCompletedAuction(String data, ArrayList<Item> items, ArrayList<Auction> completedAuctions) {
		String[] info = toArray(data);
		Item item = findItem(Integer.parseInt(info[1]), items);
		LocalDateTime openingTime = createDateTime(info[5]);
		LocalDateTime closingTime = createDateTime(info[6]);
		Auction auction = new Auction(item, Integer.parseInt(info[0]), openingTime, closingTime);
		if (item != null) item.setAvailable(false);
		auction.setActive(false);
		completedAuctions.add(auction);
		return info[4];
	}
	
	
	public static Auction createFutureAuction(String data, ArrayList<Item> items) {
		String[] info = toArray(data);
		Item item = findItem(Integer.parseInt(info[1]), items);
		LocalDateTime openingTime;
		LocalDateTime closingTime;
		if (info[2].equals("$")) {
			openingTime = LocalDateTime.now().plusMinutes(2);
			closingTime = openingTime.plusMinutes(5);
		}
		else {
			openingTime = createDateTime(info[2]);
			closingTime = createDateTime(info[3]);
		}
		Auction auction = new Auction(item, Integer.parseInt(info[0]), openingTime, closingTime);
		auction.setActive(false);
		return auction;
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
		LocalDateTime dateTime = createDateTime(info[4]);
		Bid bid = new Bid(Double.parseDouble(info[0]), findAuction(Integer.parseInt(info[1]), auctionsAdded), findCustomer(Integer.parseInt(info[2]), customersAdded), Integer.parseInt(info[3]), dateTime);
		return bid;
	}
	
	
	public static Bid createBidCompleted(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> completedAuctionsAdded) {
		String[] info = toArray(data);
		LocalDateTime dateTime = createDateTime(info[4]);
		Bid bid = new Bid(Double.parseDouble(info[0]), findAuction(Integer.parseInt(info[1]), completedAuctionsAdded), findCustomer(Integer.parseInt(info[2]), customersAdded), Integer.parseInt(info[3]), dateTime);
		return bid;
	}
	
	
	public static void populateCustomerBids(ArrayList<Customer> customers, ArrayList<String[]> bidBuffer, ArrayList<Bid> bids) {
		for (int i = 0; i < customers.size(); i++) {		
				
			String[] winningBidIDs = {};
			if (bidBuffer.get(i)[0] != null) {
				winningBidIDs = bidBuffer.get(i)[0].split("#");
			}
			
			String[] activeBidIDs = {};
			if (bidBuffer.get(i)[1] != null) {
				activeBidIDs = bidBuffer.get(i)[1].split("#");
			}
			
			String[] historicBidIDs = {};
			if (bidBuffer.get(i)[2] != null) {
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
			
			if (!processedBidIDs[0].equals("")) {
				for (int j = 0; j < processedBidIDs.length; j++) {
					auctions.get(i).getProcessedBids().push(findBid(Integer.parseInt(processedBidIDs[j]), bids));
				}
			}

			if (!unprocessedBidIDs[0].equals("") || unprocessedBidIDs.length == 0) {
				for (int j = 0; j < unprocessedBidIDs.length; j++) {
					auctions.get(i).getUnprocessedBids().enqueue(findBid(Integer.parseInt(unprocessedBidIDs[j]), bids));
				}
			}

			if (!processedBidIDs[0].equals("")) {
				Bid highest = auctions.get(i).getProcessedBids().peek();
				auctions.get(i).setCurrentHighest(highest);
			}
		}
	}
	
	
	public static void populateCompletedAuctionBids(ArrayList<String> historicBids, ArrayList<Auction> completedAuctions, ArrayList<Bid> bids) {
		for (int i = 0; i < historicBids.size(); i++) {
			String[] bidIDs = historicBids.get(i).split("#");
			
			if (!bidIDs[0].equals("")) { 
				for (int j = 0; j < bidIDs.length; j++) {
					completedAuctions.get(i).getProcessedBids().push(findBid(Integer.parseInt(bidIDs[j]), bids));
				}
			}
			
			if (!completedAuctions.get(i).getProcessedBids().isEmpty()) {
				completedAuctions.get(i).setCurrentHighest(completedAuctions.get(i).getProcessedBids().peek());
				completedAuctions.get(i).setSellingPrice(estSellingPrice(completedAuctions.get(i), bidIDs, bids));
			}
		}
	}

	/*
	 * Uses a list of bids to find establish the selling price of an item for given auction.
	 * May not be necessary if the text file already has this information present
	 */
	public static double estSellingPrice(Auction auction, String[] processedBidIDs, ArrayList<Bid> bids) {
		int search = processedBidIDs.length - 2;
		Bid b = findBid(Integer.parseInt(processedBidIDs[search]), bids);
		
		
		boolean testBoolean = b.isValid();
		double value = b.getValue();
		double value2 = auction.getCurrentHighest().getValue();
		
		boolean found = false;
		while (!found) {
			if (b.isValid() && b.getValue() < auction.getCurrentHighest().getValue()) {
				auction.setSellingPrice(b.getValue());
				found = true;
			}
			else search -= 1;
		}
		return b.getValue();
	}
	
	
	public static void addToDriver(ArrayList<Item> items, ArrayList<Auction> activeAuctions, ArrayList<Auction> completedAuctions, ArrayList<Auction> futureAuctions, ArrayList<Customer> customersAdded) {
		
		if (!items.isEmpty()) {
			ObjectALInsertionSort.insertionSort(items);
			for (int i = 0; i < items.size(); i++) {
				if (!searchDriver(Driver.items, items.get(i))) {
					Driver.items.add(items.get(i));
				}
			}
		}
		
		for (int i = 0; i < activeAuctions.size(); i++) {
			ObjectALInsertionSort.insertionSort(activeAuctions);
			if (!activeAuctions.isEmpty()) {
				if (!searchDriver(Driver.ongoingAuctions, activeAuctions.get(i))) {
					Driver.ongoingAuctions.add(activeAuctions.get(i));
				}
			}
		}
		
		for (int i = 0; i < completedAuctions.size(); i++) {
			ObjectALInsertionSort.insertionSort(completedAuctions);
			if (!completedAuctions.isEmpty()) {
				if (!searchDriver(Driver.completedAuctions, completedAuctions.get(i))) {
					Driver.completedAuctions.add(completedAuctions.get(i));
				}
			}
		}
		
		for (int i = 0; i < futureAuctions.size(); i++) {
			ObjectALInsertionSort.insertionSort(futureAuctions);
			if (!futureAuctions.isEmpty()) {
				if (!searchDriver(Driver.futureAuctions, futureAuctions.get(i))) {
					Driver.futureAuctions.add(futureAuctions.get(i));
				}
			}
		}
		
		for (int i = 0; i < customersAdded.size(); i++) {
			ObjectALInsertionSort.insertionSort(customersAdded);
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

	
	public static Bid findBid(int bidID, ArrayList<Bid> bids) {
		for (int i = 0; i < bids.size(); i++) {
			if (bidID == bids.get(i).getBidID()) {
				return bids.get(i);
			}
		}
		return null;
	}
	
	
	public static <E extends Comparable<E>> boolean searchDriver(ArrayList<E> driverList, E object) {
		for (int i = 0; i < driverList.size(); i++) {
			if (driverList.get(i).compareTo(object) == 0) {
				return true;
			}
		}
		return false;
	}

	public static String nullCheckString(String[] info, int index) {
		try {
			if (!info[index].equals("")) {
				return info[index];
			}
			throw new NullDataException("Missing Customer data: username or password not found");
		}
		catch (Exception e) {
			System.out.println("The text file didn't contain sufficient information to populate the program. "
					+ "Check to make sure that the text file includes all necessary information.");
		}
		return null;
	}

	public static int nullCheckInteger(String[] info, int index) {
		try {
			if (!info[index].equals("")) {
				return Integer.parseInt(info[index]);
			}
			throw new NullDataException("Null data at info[" + index + "]");
		}
		catch (Exception E) {
			
		}
		return 0;
	}
	

	public static double nullCheckDouble(String[] info, int index) {
		if (!info[index].equals("")) {
			return Double.parseDouble(info[index]);
		}
		else return 0.0;
	}
	

	public static LocalDateTime createDateTime(String data) {
		String[] info = data.split("#");
		int year = Integer.parseInt(info[0]);
		int month = Integer.parseInt(info[1]);
		int day = Integer.parseInt(info[2]);
		int hour = Integer.parseInt(info[3]);
		int minute = Integer.parseInt(info[4]);
		int second = Integer.parseInt(info[5]);
		
		LocalDate date = LocalDate.of(year, month, day);
		LocalTime time = LocalTime.of(hour, minute, second);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime;
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
