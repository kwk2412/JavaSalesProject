package rinosJavaSalesProject;

import java.util.Scanner;

/**
 * Contains methods that are used to validate input from the user
 * @author waveo
 *
 */

public class AccountValidation {
	
	/**
	 * Handles the logging on of both customers and admins
	 * @param privileges a String containing either "admin" or "customer"
	 *
	 */
		public void login(String privileges) {
			
			boolean validLoginAttempt = false;
			while (!validLoginAttempt) {
				
				Scanner scan = new Scanner(System.in);
				
				System.out.print("Enter username: ");
				String username = scan.nextLine();
				
				boolean validUsername = validateUsername(username, privileges);

				//Only if the username returns as something we have on file, we continue
				if (validUsername) {
					
					boolean validPassword = false;
					while (validPassword == false) {
						
						//Have the user enter a password and get a response back 
						//regarding the success of the input attempt
						System.out.print("Enter password: ");
						String password = scan.nextLine();
						
						validPassword = validatePassword(username, password);
						
						if (validPassword) {
							
							Account accountToBeLoggedIn = null;
							for (int i = 0; i < Driver.accounts.size(); i++) {
								if (Driver.accounts.get(i).username.equals(username)) {	
									accountToBeLoggedIn = Driver.accounts.get(i);
								}
							}
							//Driver.currentUser.setUser(Account accountThatJustAuthenticated);
							//I will leave the logistics of how to pass in the right account up to you guys
							Driver.currentUser.setUser(accountToBeLoggedIn);
							SystemMessage.print("Successful login attempt");
							validLoginAttempt = validPassword = true;
						}
						else {
							validPassword = validLoginAttempt = !InputMethods.yesNoToBool("Would you like to try another password? (yes or no)");
							// Anything we want to do if the password as entered doesn't 
							// the one associated with the username the user just entered
						}
					}
				}
				
				else {
					validLoginAttempt = !InputMethods.yesNoToBool("Would you like to try another username? (yes or no)");
					//Anything that we do relating to a failure in finding the username as entered
				}
			}

		}
		
		
		/**
		 * Checks to see if the username the user entered is on file
		 * @param username a String containing the username the user entered
		 * @param privileges a String containing either "admin" or "customer"
		 * @return A boolean that is true if the username is on file and false if it is not. 
		 *
		 */
		public boolean validateUsername(String username, String privileges) {
			for (Account acc : Driver.accounts) {
				if (acc.getPrivileges().equalsIgnoreCase(privileges) && acc.username.equals(username)) {
					return true;
				}
			}
		
			System.out.println("This is an incorrect username");	

			return false;
			
			//Driver.accounts is all we need to access all of the accounts 
			//It is the central repository for all accounts
		}
		
		/**
		 * Checks to see if the password the user entered matches the username they entered
		 * @param username a String containing the username the user entered
		 * @param password a String containing the password the user entered
		 * @param privileges a String containing either "admin" or "customer"
		 * @return A boolean that is true if the username matches the password and false if it does not.
		 *
		 */
		public static boolean validatePassword(String username, String password) {
			Scanner scan = new Scanner(System.in);
			Account accountToBeLoggedIn = null;
			for (int i = 0; i < Driver.accounts.size(); i++) {
				if (Driver.accounts.get(i).username.equals(username)) {	
					accountToBeLoggedIn = Driver.accounts.get(i);
				}
			}
			
			if (accountToBeLoggedIn.getPassword().equals(password)) {
				return true;
			}
			else {
				System.out.println("This is an incorrect password");
				return false;
			}
			
		}
	
}
