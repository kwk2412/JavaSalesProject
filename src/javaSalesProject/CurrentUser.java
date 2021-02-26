package javaSalesProject;

public class CurrentUser {
	
	private Account user;
	
	public String toString() {
		return "The user that is currently logged in is: \n" + user.toString();
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
		if (user != null) System.out.println("The currently logged in user has been set to: " + user.username);
		else System.out.println("Current user has been set to null");
	}
	
}
