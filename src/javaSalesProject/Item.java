package javaSalesProject;

public class Item {
	
  private double startingPrice;
	private String name;
	private double priceSold;
	private final int INCREMENT;
	private int itemID;
	private int nextNum = 100;
	
	
	
	public Item() {
		INCREMENT = 100;
		itemID = nextNum;
		nextNum++;
	}
	
	
	public Item(double startingPrice, String name, int INCREMENT) {
		this.startingPrice = startingPrice;
		this.name = name;
		this.INCREMENT = INCREMENT;
		itemID = nextNum;
		nextNum++;
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
		return INCREMENT;
	}
	
}
