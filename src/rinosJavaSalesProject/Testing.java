package javaSalesProject;

import java.util.ArrayList;
import java.util.Scanner;

// This class is only used to store methods we may find useful when testing/debugging our program
// Nothing is to be put in here that the program needs to run under normal circumstances

public class Testing {
	
	public static void printAdminAccounts() {
		Admin example = new Admin();
		System.out.println("Admin Accounts:");
		for (Account acc : Driver.accounts) {
			if (acc.getClass().equals(example.getClass())) {
				System.out.println(acc.toString());
				System.out.println();
			}
		}
	}

	public static void printCustomerAccounts() {
		Customer example = new Customer();
		System.out.println("Customer Accounts:");
		for (Account acc : Driver.accounts) {
			if (acc.getClass().equals(example.getClass())) {
				System.out.println(acc.toString());
				System.out.println();
			}
		}
	}
	
	public static void printAllAccounts() {
		System.out.println("All accounts:");
		for(Account acc: Driver.accounts) {
			System.out.println(acc.toString());
			System.out.println();
		}
	}
	
	//The menu for the code that Clay wrote in the early phases of the program's creation
	//Created for the purpose of demonstrating how these methods would be called and used
	public static void menuClay() {
		
		Scanner keyboard = new Scanner(System.in);
		boolean done = false;
		while(!done) {
			System.out.println("1. create customer account. 2. Create admin account. 3. print all accounts. 4. Show current user"
					+ " 5. Input accounts to text file. 6. Output accounts to a text file.");
			int selection = keyboard.nextInt();
			if(selection == 1) {
				CreateAccount.createCustomerAccount();
			} else if(selection == 2) {
				CreateAccount.createAdminAccount();
			} else if(selection == 3) {
				Testing.printAllAccounts();
			} else if(selection == 4) {
				System.out.println(Driver.currentUser.toString());
			} else if(selection == 5) {
				ReadWriteAccounts.inputAccounts();
			} else if(selection == 6) {
				ReadWriteAccounts.outputAccounts();	
			} else
				done = true;
		}
	}
	
	
	public void testingGeneralValidation(int lowerBound, int upperBound) {
		Scanner scan = new Scanner(System.in);
		
		int choice = 0;
		
		boolean valid = false;
		while (valid == false) {
		
			try {
				
				//This is an issue because this does not allow for a general method
				//printAdminMenu();
				
				
				System.out.print("Select an option from the menu: ");
				choice = scan.nextInt();
				
				//if (choice >= lowerBound && choice <= upperBound) valid = true;
				if (choice <= lowerBound || choice >= upperBound) {
					throw new InvalidInputException();
				}
				
				else System.out.println("Please select an option from the menu");
			}
			
			catch (InvalidInputException iie) {
				
			}
			
			catch (Exception e) {
				System.out.println("Please make sure your input is something from the menu");
				scan.nextLine();
			}	
		}
	}
	

}
