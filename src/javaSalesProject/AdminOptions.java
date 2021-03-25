package javaSalesProject;

public class AdminOptions {
	
	public static void adminMenu() {
		
		Admin a = (Admin) Driver.currentUser.getUser();
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.adminMenu();
			
			//List current ongoing auctions
			if (choice == 1) {
				a.showOngoingAuctions();
			}
			
			//Chose an ongoing auction and check the bidding history
			else if (choice == 2) {
				System.out.println("You selected option 2");
			}
			
			//List information about completed auctions
			else if (choice == 3) {
				printCompletedAuctions();
			}
			
			//Summary data of winnings bids
			else if (choice == 4) {
				System.out.println("You selected option 4");
			}
			
			//Add and activate a new auction
			else if (choice == 5) {
				a.startNewAuction();
				//AuctionMethods.startNewAuction();
			} 
			
			// Create a new admin account. 
			else if (choice == 6) {
				CreateAccount.createAdminAccount();
				Driver.loginAttemptCheck(menu);
				
			}
			//Return to main menu
			else if (choice == 7) {
				menu = false;
			}
			
		}
	}
	
	private static void printCompletedAuctions() {
		System.out.println("Summary of Completed Auctions: ");
		if (Driver.completedAuctions.size()>1) {
			for (int i = 0; i < Driver.completedAuctions.size(); i++) {
				System.out.println(Driver.completedAuctions.get(i).toString());
			}
		}
		else {
			System.out.println("There are no completed Auctions at this time.");
		}
	}

}
