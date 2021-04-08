package javaSalesProject;

import java.util.Scanner;
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

	public void startNewAuction() {
		if (Driver.items.size() > 0) {
			Item toBeSold = null;
			LocalDateTime startDateTime = null;
			LocalDateTime endDateTime = null;

			toBeSold = getItemToBeSold();
			if (toBeSold != null) {
				startDateTime = getAuctionStartDateTime();
				if (startDateTime != null) {
					endDateTime = getAuctionEndDateTime();
					if (endDateTime != null) {

						Auction a = new Auction(toBeSold, startDateTime, endDateTime);
						SystemMessage.print("Auction Added:\n" + a.toString());
						Driver.futureAuctions.add(a);
					}
				}
			}
		} else {
			System.out.println("No auction created");
		}
	}

	private Item getItemToBeSold() {
		int indexOfItem = Menu.pickItemMenu();
		if (indexOfItem == -1) {
			return null;
		} else
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
		LocalDateTime sdt = null;
		// When would you like the auction to start?
		int input = Menu.startDateMenu();

		if (input == 1) { // Immediately
			sdt = LocalDateTime.now();
		} else if (input == 2) { // Later today
			LocalDate ld = LocalDate.now();
			LocalTime lt = getAuctionStartTime();
			sdt = LocalDateTime.of(ld, lt);
		} else if (input == 3) { // A later date
			LocalDate ld = getAuctionStartDate();
			LocalTime lt = getAuctionStartTime();
			sdt = LocalDateTime.of(ld, lt);

		}
		return sdt;
	}

	public LocalDateTime getAuctionEndDateTime() {
		LocalDateTime edt = null;
		// When would you like the auction to end?
		int input = Menu.endDateMenu();
		if (input == 1) { // Later today
			LocalDate ld = LocalDate.now();
			LocalTime lt = getAuctionEndTime();
			edt = LocalDateTime.of(ld, lt);
		} else if (input == 2) { // A later date
			LocalDate ld = getAuctionEndDate();
			LocalTime lt = getAuctionEndTime();
			edt = LocalDateTime.of(ld, lt);
		}
		return edt;
	}

	public LocalTime getAuctionStartTime() {
		LocalTime startTime = null;
		int minute = -1;
		int hour = InputMethods.getIntOrReturnNeg1(1, 12, "What hour would you like the auction to start?");
		if (hour > 0) {
			minute = InputMethods.getIntOrReturnNeg1(0, 59, "What minute would you like the auction to start?");
			if (minute >= 0) {
				int amPm = Menu.amPmMenu();
				if (amPm == 2) { // pm
					startTime = LocalTime.of(hour + 12, minute);
					return startTime;
				} else if (amPm == 1) { // am
					if (hour == 12) {
						hour = 0;
					}
					startTime = LocalTime.of(hour, minute);
				}
			}
		}
		return null;
	}

	public LocalDate getAuctionStartDate() {
		LocalDate startDate = null;
		// Get a year from this year to this year + 3 years
		int dayOfMonth = -1;
		Month month = null;
		String yearQuestion = "What year would you like the auction to start? (" + LocalDate.now().getYear() + " - "
				+ LocalDate.now().plusYears(3).getYear() + ")";
		int year = InputMethods.getIntOrReturnNeg1(LocalDate.now().getYear(), LocalDate.now().plusYears(3).getYear(),
				yearQuestion);
		String dayQuestion = "What day would you like the auction to start? (" + "1" + " - "
				+ LocalDate.now().getMonth().maxLength() + ")";
		if (year > 0) {
			int mon = InputMethods.getIntOrReturnNeg1(1, 12, "What month would you like the auction to start");
			if (mon > 0) {
				month = Month.of(mon);
				dayOfMonth = InputMethods.getIntOrReturnNeg1(1, month.maxLength(), dayQuestion);
				if (dayOfMonth > 0) {
					startDate = LocalDate.of(year, month, dayOfMonth);
					return startDate;
				}
			}
		}
		return null;
	}

	public LocalTime getAuctionEndTime() {
		LocalTime endTime = null;
		int minute = -1;
		int hour = InputMethods.getIntOrReturnNeg1(1, 12, "What hour would you like the auction to end?");
		if (hour > 0) {
			minute = InputMethods.getIntOrReturnNeg1(0, 59, "What minute would you like the auction to end?");
			if (minute >= 0) {
				int amPm = Menu.amPmMenu();
				if (amPm == 2) { // pm
					endTime = LocalTime.of(hour + 12, minute);
					return endTime;
				} else if (amPm == 1) { // am
					if (hour == 12) {
						hour = 0;
					}
					endTime = LocalTime.of(hour, minute);
				}
			}
		}
		return null;
	}

	public LocalDate getAuctionEndDate() {
		LocalDate endDate = null;

		int dayOfMonth = -1;
		Month month = null;
		String dayQuestion = "What day would you like the auction to end? (" + "1" + " - "
				+ LocalDate.now().getMonth().maxLength() + ")";
		String yearQuestion = "What year would you like the auction to end? (" + LocalDate.now().getYear() + " - "
				+ LocalDate.now().plusYears(6).getYear() + ")";

		// Get a year from this year to this year + 6 years
		int year = InputMethods.getIntOrReturnNeg1(LocalDate.now().getYear(), LocalDate.now().plusYears(6).getYear(),
				yearQuestion);

		if (year > 0) {
			int mon = InputMethods.getIntOrReturnNeg1(1, 12, "What month would you like the auction to start");
			if (mon > 0) {
				month = Month.of(mon);
				dayOfMonth = InputMethods.getIntOrReturnNeg1(1, month.maxLength(), dayQuestion);
				if (dayOfMonth > 0) {
					endDate = LocalDate.of(year, month, dayOfMonth);
					return endDate;
				}
			}
		}
		return null;
	}

}
