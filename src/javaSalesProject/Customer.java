package javaSalesProject;

import java.util.Scanner;

public class Customer extends Account {
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String privileges) {
		super(username, password, privileges);
		
	}
	
	// Constructor used when (re)creating accounts imported from a text file
	public Customer(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);
		
	}

}

