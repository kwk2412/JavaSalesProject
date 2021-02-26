package javaSalesProject;

import java.util.Scanner;

public class InputMethods {
	public static boolean yesNoToBool(String question) {
		Scanner keyboard = new Scanner(System.in);
		
		boolean validInput = false;
		boolean threwException = false;
		
		String input = null;
		while(!validInput) {
		
			threwException = false;
			
			try {
				input = keyboard.nextLine();
				if(!input.trim().equalsIgnoreCase("yes") && !input.trim().equalsIgnoreCase("no")) {
					throw new InvalidInputException();
				}
			}
			
			catch (InvalidInputException iie){
				threwException = true;
			}
			
			catch (Exception e) {
				threwException = true;
			}
			
			if(threwException) {
				System.out.println("Invalid entry. Try again");
				System.out.println(question);
			}
			
			else {
				validInput = true;
			}
		}
		
		if(input.trim().equalsIgnoreCase("yes")) {
			return true;
		}
		else {
			return false;
		}
	}

}
