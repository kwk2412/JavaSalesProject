package javaSalesProject;

import java.util.Scanner;

public class Admin extends Account{
	
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
	
	
}
