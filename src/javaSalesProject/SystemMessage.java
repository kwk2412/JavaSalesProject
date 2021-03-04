package javaSalesProject;

public class SystemMessage {
	
	private String message;
	
	
	public SystemMessage(String message) {
		this.message = message;
		printSystemMessage();
	}
	
	public void printSystemMessage() {
		System.out.println("*SYSTEM MESSAGE: " + message);
	}

}
