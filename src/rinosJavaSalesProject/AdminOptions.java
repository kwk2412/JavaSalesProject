package rinosJavaSalesProject;

public class AdminOptions {

	public static void adminMenu() {

		Admin a = (Admin) Driver.currentUser.getUser();
		boolean menu = true;
		while (menu) {

			int choice = Menu.adminMenu();

			// List current ongoing auctions
			if (choice == 1) {
				a.showOngoingAuctions();
			}

			// List future auctions
			else if (choice == 2) {
				a.showFutureAuctions();
			}

			// Chose an ongoing auction and check the bidding history
			else if (choice == 3) {
				a.printAuctionBids();
			}

			// List information about completed auctions
			else if (choice == 4) {
				a.printCompletedAuctions();
			}

			// Summary data of winnings bids
			else if (choice == 5) {
				a.printWinningBids();
			}

			// Add and activate a new auction
			else if (choice == 6) {
				a.createAuction();
				// AuctionMethods.startNewAuction();
			}

			// Create a new admin account.
			else if (choice == 7) {
				CreateAccount.createAdminAccount();
				Driver.loginAttemptCheck(menu);
			}
			// Return to main menu
			else if (choice == 8) {
				menu = false;
			}
		}
	}
}
