Readme

Main menu
	- The main menu has two versions - one for when a user is logged into the application and another for when no user is present	
	
	No user present:
	- 1) Loads sample data into the items arraylist
	- 2) Doesn't do anything yet
	- 3) Prints the items in the items arraylist
	- 4) Initiates admin login process
	- 5) Initiates customer login process
	- 6) Exit the application after asking for user's confirmation
	
	User Present:
	- 1) Loads items array list with sample data
	- 2) Doesn't do anything yet
	- 3) Prints the items present in the items array list
	- 4) Logs the user out of the application (input "yes" at the prompt to affirm)
	- 5) Advances the user to the next menu
	- 6) Exits the application
	
Logging in
	- Whether you choose to log in as an admin or a customer, the process is the same.  
	  You are first prompted to input the username associated with the account you want to log in as.
	  You are given a sub menu which allows you to specify your login type. The breakdown of the 
	  options is as follows:
	  
	  Login sub menus
	  	- 1) Will compare your login credentials to the ones we have logged
	  	- 2) Initiates the creation process of a new account
	  	- 3) Returns to the previous menu
	  	
	  	Returning Account
	  		- Prompts you for login credentials and compares them to what the application has logged
	  		- Enter the username and then the password
	  	
	  	New Account
	  		- You are prompted to input the username you want to be associated with your account
	  		- After username input, you are prompted for the password you want to be associated with your account
	  		- You will be logged in as that account upon account creation
	  		
Admin Menu
	- The only menu option that is working is the transition back to the main menu
	
Customer Menu
	- The only menu option that is working is the transition back to the main menu
