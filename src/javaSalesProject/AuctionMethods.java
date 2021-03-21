package javaSalesProject;

public class AuctionMethods {

	

	/*
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
				}
				else SystemMessage.print("That auction is not active");
			}
		}
		else {
			System.out.println("There are no ongoing auctions");
		}
	}
	*/
	
}
