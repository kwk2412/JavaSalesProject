
package javaSalesProject;

import java.text.NumberFormat;

public class Item implements Comparable<Item> {
	
	private double startingPrice;
	private String name;
	private double priceSold;
	private int increment;
	private int itemID;
	private static int nextNum = 100;
	private boolean available = true;
	private boolean paidFor;
	
	
	public Item() {
		increment = 100;
		itemID = nextNum;
		nextNum++;
	}
	
	
	public Item(double startingPrice, String name, int increment) {
		this.startingPrice = startingPrice;
		this.name = name;
		this.increment = increment;
		itemID = nextNum;
		nextNum++;
	}
	
	public Item(double startingPrice, String name, int increment, int itemID, boolean paidFor) {
		this.startingPrice = startingPrice;
		this.name = name;
		this.increment = increment;
		this.itemID = itemID;
		this.paidFor = paidFor;
	}
	
	public String toString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		return "\tItem name: " + name + "\n" + 
			   "\tStarting Price: " + cf.format(startingPrice) + "\n" +
			   "\tItem ID: " + itemID + "\n" + 
			   "\tIncrement: " + increment + "\n";
		
	}
	
	public String selectAuctionToString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		return "\tItem name: " + name + "\n" + 
			   "\tStarting Price: " + cf.format(startingPrice) + "\n" +
			   "\tIncrement: " + increment + "\n";
	}
	
	
	public int compareTo(Item e) {
		if (e.getItemID() > this.getItemID())
			return 1;
		else if (e.getItemID() < this.getItemID()) 
			return -1;
		else
			return 0;
	}

	
	public double getStartingPrice() {
		return startingPrice;
	}


	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPriceSold() {
		return priceSold;
	}


	public void setPriceSold(double priceSold) {
		this.priceSold = priceSold;
	}


	public int getIncrement() {
		return increment;
	}


	public int getItemID() {
		return itemID;
	}


	public void setItemID(int itemID) {
		this.itemID = itemID;
	}


	public void setIncrement(int increment) {
		this.increment = increment;
	}


	public boolean isAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}


	public boolean isPaidFor() {
		return paidFor;
	}


	public void setPaidFor(boolean paidFor) {
		this.paidFor = paidFor;
	}
	
}
