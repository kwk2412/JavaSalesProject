package javaSalesProject;

import java.text.NumberFormat;

public class Item {
	
	private double startingPrice;
	private String name;
	private double priceSold;
	private int increment;
	private int itemID;
	private static int nextNum = 100;
	
	
	
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
	
	
	

}
