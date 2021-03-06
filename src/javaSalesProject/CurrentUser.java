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
		new SystemMessage("The current user has been set to: " + user.username);
		if (Driver.currentUser.getUser().userID == 1) {
			new SystemMessage("No one is logged in");
		}
	}
	
}
