package javaSalesProject;

public class AdminOptions {
	
	public static void adminMenu() {
		
		
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.adminMenu();
			
			//List current ongoing auctions
			if (choice == 1) {
				System.out.println(AuctionMethods.showOngoingAuctions());
			}
			
			//Chose an ongoing auction and check the bidding history
			else if (choice == 2) {
				System.out.println("You selected option 2");
			}
			
			//List information about completed auctions
			else if (choice == 3) {
				System.out.println("You selected option 3");
			}
			
			//Summary data of winnings bids
			else if (choice == 4) {
				System.out.println("You selected option 4");
			}
			
			//Add and activate a new auction
			else if (choice == 5) {
				AuctionMethods.startNewAuction();
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

}
