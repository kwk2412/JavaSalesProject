Readme

Main menu

	- The main menu has two versions - one for when a user is logged into the application and another for when no user is present	
	
	No user present:
	- 1) Loads sample data into the items arraylist
	- 2) Doesn't do anything yet
	- 3) Prints the items in the items arraylist
	- 4) Initiates admin login process
	- 5) Initiates customer login process
	- 6) Runs the specialized proof of concept for checkpoint C
	- 7) Exit the application after asking for user's confirmation
		
		Specialized POC for checkpoint C:
			This is a pre-programmed auction that runs on its own from start to finish. Customers, an auction, and bids are 
			created and then handled and processed without input from the user. The method that starts this process is called 
			"checkpointC()" and is located just after the main method in Driver.java, at line 31. After each bid is processed, 
			a summary of the auction's status is printed to the screen.
	
	User Present:
	- 1) Loads items array list with sample data
	- 2) Doesn't do anything yet
	- 3) Prints the items present in the items array list
	- 4) Logs the user out of the application (input "yes" at the prompt to affirm)
	- 5) Advances the user to the next menu
	- 6) Exits the application
	
Logging in

	- When the user chooses to log in as a customer, a sub menu shows up. From here the user can choose
	  to create a new customer account, login as an existing customer account, or return to the previous menu.
	  
	- The breakdown of the options is as follows:
	  
	  Login sub menus
	  	- 1) Will compare your login credentials to those of the accounts that we have logged
	  	- 2) Initiates the creation process of a new account
	  	- 3) Returns to the previous menu
	  	
	  	Returning Account
	  		- Prompts you for login credentials and compares them to what the application has logged
	  		- Enter the username and then the password
	  	
	  	New Account
	  		- You are prompted to input the username you want to be associated with your account
	  		- After username input, you are prompted for the password you want to be associated with your account
	  		- You will be logged in as that account upon account creation
	  
	- Logging in as an admin is a bit different because only admins can create new admin accounts. 
	  A default admin account is already created, the credentials to which are as follows:
	  
		username: Admin
		password: password
	  	  		
Admin Menu
	- The only menu options that are working are the transition back to the main menu
	  and the creation of a new admin account
	
Customer Menu
	- The only menu option that is working is the transition back to the main menu
	
