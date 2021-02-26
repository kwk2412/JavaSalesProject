package javaSalesProject;

import java.util.ArrayList;

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

}
