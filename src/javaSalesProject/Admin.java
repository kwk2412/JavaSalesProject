package javaSalesProject;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class Admin extends Account {

	public Admin() {
		super();
	}

	public Admin(String username, String password, String privileges) {
		super(username, password, privileges);
	}

	// Constructor used when (re)creating accounts imported from a text file
	public Admin(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);

	}

	public void createAuction() {
		if (Driver.items.size() > 0) {
			LocalDateTime startDateTime = null;
			LocalDateTime endDateTime = null;

			Item item = getItemToBeSold();
			if (item != null) {
				startDateTime = getAuctionStartDateTime();
				if (startDateTime != null) {
					
					boolean valid = false;
					while (valid == false) {
						endDateTime = getAuctionEndDateTime();
						if (endDateTime.isAfter(startDateTime)) 
							valid = true;
						else System.out.println("End date cannot be before start date.\n");
					}
					
					if (endDateTime != null) {
						Auction a = new Auction(item, startDateTime, endDateTime);
						SystemMessage.print("Auction Added:\n" + a.toString());
						Driver.futureAuctions.add(a);
					}
				}
			}
		}
		else {
			System.out.println("An auction cannot be created because there are no items for which to create an auction");
		}
	}

	private Item getItemToBeSold() {
		int indexOfItem = Menu.pickItemMenu();
		if (indexOfItem == -1) {
			return null;
		}
		else
			return Driver.items.get(indexOfItem);
	}

	public void showOngoingAuctions() {
		if (Driver.ongoingAuctions.size() == 0) {
			System.out.println("There are no ongoing auctions");
		} else {
			System.out.println("Ongoing Auctions");
			System.out.println("=================");
			for (int i = 0; i < Driver.ongoingAuctions.size(); i++) {
				System.out.println(Driver.ongoingAuctions.get(i).toString());
			}
		}
	}

	public void showFutureAuctions() {
		if (Driver.futureAuctions.size() == 0) {
			System.out.println("There are no future auctions");
		} else {
			System.out.println("Future Auctions");
			System.out.println("=================");
			for (int i = 0; i < Driver.futureAuctions.size(); i++) {
				System.out.println(Driver.futureAuctions.get(i).toString());
			}
		}
	}
	
	public LocalDateTime getAuctionStartDateTime() {
		LocalDateTime startDateTime = null;
		
		int input = Menu.startDateMenu();

		if (input == 1) { // Immediately
			startDateTime = LocalDateTime.now();
		}
		else if (input == 2) { // Later today
			LocalDate ld = LocalDate.now();
			LocalTime lt = markAuctionTime("start");
			startDateTime = LocalDateTime.of(ld, lt);
		}
		else if (input == 3) { // A later date
			LocalDate ld = markAuctionDate("start");
			LocalTime lt = markAuctionTime("start");
			startDateTime = LocalDateTime.of(ld, lt);
		}
		return startDateTime;
	}

	
	public LocalDateTime getAuctionEndDateTime() {
		LocalDateTime edt = null;

		int input = Menu.endDateMenu();
		
		if (input == 1) { // Later today
			LocalDate ld = LocalDate.now();
			LocalTime lt = markAuctionTime("end");
			edt = LocalDateTime.of(ld, lt);
		}
		else if (input == 2) { // A later date
			LocalDate ld = markAuctionDate("end");
			LocalTime lt = markAuctionTime("end");
			edt = LocalDateTime.of(ld, lt);
		}
		return edt;
	}
	
	public LocalTime markAuctionTime(String mark) {
		int hour = InputMethods.getIntOrReturnNeg1(1, 12, "What hour would you like the auction to " + mark + "?");
		int minute = InputMethods.getIntOrReturnNeg1(0, 59, "What minute would you like the auction to " + mark + "?");
		LocalTime time = specifyHour(hour, minute);
		return time;
	}
	
	
	public LocalDate markAuctionDate(String mark) {
		LocalDate date = null;
		int year = 0;
		Month month = null;
		int dayOfMonth = 0;
		
		String dayQuestion = "What day would you like the auction to " + mark + "? (" + "1" + " - "
				+ LocalDate.now().getMonth().maxLength() + ")";
		String yearQuestion = "What year would you like the auction to end? (" + LocalDate.now().getYear() + " - "
				+ LocalDate.now().plusYears(6).getYear() + ")";

		// Get a year from this year to this year + 6 years
		year = InputMethods.getIntOrReturnNeg1(LocalDate.now().getYear(), LocalDate.now().plusYears(6).getYear(),
				yearQuestion);
		
		if (year > 0) {
			int monthInt = InputMethods.getIntOrReturnNeg1(1, 12, "What month would you like the auction to " + mark + "?");
			if (monthInt > 0) {
				month = Month.of(monthInt);
				dayOfMonth = InputMethods.getIntOrReturnNeg1(1, month.maxLength(), dayQuestion);
				if (dayOfMonth > 0) {
					date = LocalDate.of(year, month, dayOfMonth);
				}
			}
		}

		return date;
		
	}

	/*
	 * Takes in a hour and a minute and asks the user these numbers refer to am or pm
	 * returns a LocalTime object that reflects the user's choice
	 */
	public LocalTime specifyHour(int hour, int minute) {
		LocalTime endTime;
		int amPm = Menu.amPmMenu();
		
		if (amPm == 1) { // am
			if (hour == 12) hour = 0;
			endTime = LocalTime.of(hour, minute);
		}
		else { // pm
			if (hour != 12) {
				endTime = LocalTime.of(hour + 12, minute);
			}
			else {
				endTime = LocalTime.of(hour, minute);
			}
		}
		return endTime;
	}

	public int compareTo(Account o) {
		if (this.userID < o.userID) {
			return 1;
		}
		else if (this.userID > o.userID) {
			return -1;
		}
		else
			return 0;
	}
	
	public void printCompletedAuctions() {
		System.out.println("Summary of Completed Auctions: ");
		if (Driver.completedAuctions.size() >= 1) {
			for (int i = 0; i < Driver.completedAuctions.size(); i++) {
				System.out.println(Driver.completedAuctions.get(i).toString());
			}
		} else {
			System.out.println("There are no completed Auctions at this time.");
		}
	}
	
	public void printWinningBids() {
		if (!Driver.completedAuctions.isEmpty()) {
			for (int i = 0; i < Driver.completedAuctions.size(); i++) {
				if (Driver.completedAuctions.get(i).getProcessedBids().peek() != null) {
					System.out.println("Auction " + (i + 1));
					System.out.println(Driver.completedAuctions.get(i).getCurrentHighest().toString());	
				}
			}
		}
		else {
			System.out.println("No winning bids - no auctions have been completed");
		}
	}
	
	public void printAuctionBids() {
		if (!Driver.ongoingAuctions.isEmpty()) {
			int auctionIndex = Menu.selectAuction();
			Stack<Bid> clone = Driver.ongoingAuctions.get(auctionIndex - 1).getProcessedBids().clone();
			for (int i = 0; i < clone.size(); i++) {
				System.out.println(clone.pop().toString());
			}
		}
		else {
			System.out.println("There are no ongoing auctions to check the bidding history for");
		}
	}

}
