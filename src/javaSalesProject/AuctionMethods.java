package javaSalesProject;

public class AuctionMethods {

	public static void startNewAuction() {
		if (Driver.items.size() > 0) {
			Item toBeSold = getItemToBeSold();
			if (toBeSold != null) {
				Auction a = new Auction(toBeSold);
				Driver.ongoingAuctions.add(a);
				SystemMessage.print("Auction Added:\n" + a.toString());
			}
		} else {
			System.out.println("The inventory is empty");
		}
	}

	private static Item getItemToBeSold() {
		int indexOfItem = Menu.pickItemMenu();
		if (indexOfItem == -1) {
			return null;
		} else
			return Driver.items.get(indexOfItem);
	}

	public static String showOngoingAuctions() {
		if (Driver.ongoingAuctions.size() == 0) {
			return "There are no ongoing auctions";
		} else {
			String result = "";
			for (int i = 0; i < Driver.ongoingAuctions.size(); ++i) {
				result += Driver.ongoingAuctions.get(i).toString() + "\n";
			}
			return result;
		}
	}

	public static void bidOnAnItem() {
		if (Driver.ongoingAuctions.size() != 0) {
			int auctionIndex = Menu.selectAuction();
			if (auctionIndex >= 0) {
				Auction auction = Driver.ongoingAuctions.get(auctionIndex);
				double value = InputMethods.getPositiveDouble("Enter the dollar amount of your bid");

				Bid b = new Bid(value, auction, (Customer) Driver.currentUser.getUser());
				SystemMessage.print("Processing Bid: \n" + b.toString());

				if (auction.isActive()) {
					auction.process(b);
					auction.printAuctionStatus();
				} else
					SystemMessage.print("That auction is not active");

			}
		}
		else {
			System.out.println("There are no ongoing auctions");
		}
	}

}
