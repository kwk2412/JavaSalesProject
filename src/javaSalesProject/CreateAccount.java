package javaSalesProject;

import java.util.Scanner;

/**
 * 
 * @author waveo Here we will put all methods that have to do with the creation
 *         of a new account The creation of a user account gets its own class
 *         for organizational purposes (we want as much stuff out of the driver
 *         as we can) and because it doesn't make sense to put methods
 *         pertaining to the creation of an account within the Account class
 *         because to run those methods you need to already have an object
 *         created. Giving these methods their own class solves both of these
 *         issues.
 * 
 *         All input validation regarding user input when prompted for a
 *         username should happen here as well
 *
 */
public abstract class CreateAccount {

	// This method will create a new customer account
	public static void createCustomerAccount() {
		String username = getUsername("customer");
		String password = null;
		if (username != null) {
			password = getPassword("customer");
		}
		if (password != null) {
			Driver.currentUser.setUser(new Customer(username, password, "customer"));
		}
	}

	// This method will create a new admin account
	public static void createAdminAccount() {
		String username = getUsername("admin");
		String password = null;
		if (username != null) {
			password = getPassword("admin");
		}
		if (password != null) {
			Driver.currentUser.setUser(new Admin(username, password, "admin"));
		}
	}

	// This method should ask the user for his intended username
	// and overwrite the instance variable username with what
	// the user inputs

	// At some point input validation will have to happen as well
	// because we should include requirements on how they should
	// format their name

	public static String getUsername(String privileges) {
		String username = null;
		int resultOfValidation = 0;
		boolean threwException = false;
		boolean done = false;
		while (!done) {
			threwException = false;
			System.out.println("What username do you want to have? (it cannot have a | character)");
			try {
				Scanner scan = new Scanner(System.in);
				username = scan.nextLine();
				resultOfValidation = validateUsername(privileges, username);
				if (resultOfValidation > 0) {
					throw new InvalidInputException();
				}
			} catch (InvalidInputException iie) {
				threwException = true;
				if (resultOfValidation == 1) {
					System.out.println("That username is already taken.");
				} else {
					System.out.println("That username contains an illegal character.");
				}
			} catch (Exception e) {
				threwException = true;
				System.out.println("That username is not valid.");
			}

			if (threwException) {
				System.out.println("Would you like to try another username? (yes or no)");
				done = !InputMethods.yesNoToBool("Would you like to try another username? (yes or no)");
			} else {
				return username;
			}
		}
		return null;
	}

	// This method should ask the user to create their password
	// What the user inputs (after we verify that it meets complexity
	// requirements and we have had them retype their password to ensure
	// its integrity) it will overwrite the password instance variable
	// with what was specified.
	public static String getPassword(String privileges) {
		String password = null;
		int resultOfValidation = 0;
		boolean threwException = false;
		boolean done = false;
		while (!done) {
			threwException = false;
			System.out.println("What password do you want to have? (it cannot have a | character)");
			try {
				Scanner scan = new Scanner(System.in);
				password = scan.nextLine();
				resultOfValidation = validatePassword(password);
				if (resultOfValidation > 0) {
					throw new InvalidInputException();
				}
			} catch (InvalidInputException iie) {
				threwException = true;
				if (resultOfValidation == 1) {
					System.out.println("That password contains an illegal character.");
				}
			} catch (Exception e) {
				threwException = true;
				System.out.println("That password is not valid.");
			}

			if (threwException) {
				System.out.println("Would you like to try another password? (yes or no)");
				done = !InputMethods.yesNoToBool("Would you like to try another password? (yes or no)");
			} else {
				return password;
			}
		}
		return null;
	}

	// here is a method dedicated to making sure the username the user
	// entered is correct and meets our standards (capital first letter,
	// no special characters, etc.)
	// returns 2 when it contains an illegal character
	// returns 1 when the username is already taken
	// returns 0 when the username is valid
	public static int validateUsername(String privileges, String username) {

		boolean containsPipe = false;
		boolean alreadyTaken = false;

		for (int i = 0; i < username.length(); ++i) {
			if (username.charAt(i) == '|') {
				containsPipe = true;
			}
		}

		for (Account acc : Driver.accounts) {
			if (acc.getPrivileges().equals(privileges)) {
				if (acc.getUsername().equals(username)) {
					alreadyTaken = true;
				}
			}
		}

		if (containsPipe) {
			return 2;
		} else if (alreadyTaken) {
			return 1;
		} else {
			return 0;
		}

	}

	// This method must make sure the password the user tried to enter
	// meets all requirements we have for password creation. Returns a int
	// reflecting the password validation's status
	// returns a 1 if the password contains an illegal character
	// returns a 0 if the password is valid
	// Could return a boolean but is set up this way in case we decide to put other requirements on a password
	public static int validatePassword(String password) {

		boolean containsPipe = false;

		for (int i = 0; i < password.length(); ++i) {
			if (password.charAt(i) == '|') {
				containsPipe = true;
			}
		}

		if (containsPipe) {
			return 1;
		} else {
			return 0;
		}
	}

}
