package javaSalesProject;

import java.util.Scanner;

public class InputMethods {
	public static boolean yesNoToBool(String question) {
		Scanner keyboard = new Scanner(System.in);
		
		boolean validInput = false;
		
		String input = null;
		while(!validInput) {
			
			try {
				System.out.println(question);
				input = keyboard.nextLine();
				if(!input.trim().equalsIgnoreCase("yes") && !input.trim().equalsIgnoreCase("no")) {
					throw new InvalidInputException();
				}
				validInput = true;
			}
			catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please enter either a yes or no");
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please enter either a yes or no");
			}
		}
		if (input.trim().equalsIgnoreCase("yes")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static double getPositiveDouble(String question) {
		double dub = -1;
		boolean done = false;
		while (!done) {
			try {
				System.out.println(question);
				Scanner scan = new Scanner(System.in);
				dub = scan.nextDouble();
				if (dub <= 0) {
					throw new InvalidInputException();
				}
				done = true;
			}
			catch(InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Your bid must be greater than 0");
				done = !yesNoToBool("Would you like to try again?");
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
				done = !yesNoToBool("Would you like to try again?");
			}
		}
		return dub;
	}
	
	public static int getIntFromMenu(int lower, int upper, String menu) {
		int choice = 0;
		boolean done = false;
		while (!done) {
			try {
				System.out.println(menu);
				Scanner scan = new Scanner(System.in);
				choice = scan.nextInt();
				if(choice < lower || choice > upper)
					throw new InvalidInputException();
				done = true;
			}
			catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please input a number between " + lower + " and " + upper);
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
			}
		}
		return choice;
	}
	
	public static int getIntOrReturnNeg1(int lower, int upper, String menu) {
		int choice;
		boolean done = false;
		while (!done) {
			try {
				System.out.println(menu);
				Scanner scan = new Scanner(System.in);
				choice = scan.nextInt();
				if(choice < lower || choice > upper)
					throw new InvalidInputException();
				return choice;
			}
			catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please input a number between " + lower + " and " + upper);
				done = !yesNoToBool("Would you like to try again?");
			}
			catch (Exception e) {
				choice = -1;
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
				done = !yesNoToBool("Would you like to try again?");
			}
		}
		return -1;
	}
	
	

}
