package javaSalesProject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*
// returns a 1 if the password contains an illegal character
	// returns a 0 if the password is valid
	// Could return a boolean but is set up this way in case we decide to put other
	// requirements on a password
	  
	 
	public static int validatePassword(String password) {

		boolean containsPipe = false;
		boolean containsBracket = false;

		for (int i = 0; i < password.length(); ++i) {
			if (password.charAt(i) == '|') {
				containsPipe = true;
			}
			if(password.charAt(i) == '}') {
				containsBracket = true;
			}
		}

		if (containsPipe || containsBracket) {
			return 1;
		} else {
			return 0;
		}
	}
*/

class JUnit_validatePassword_Test1 {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		String invalidPw = "pass|word";
		int result = CreateAccount.validatePassword(invalidPw);
		if(result == 0) {
			fail("The password should have been invalid due to the pipe character");
		} 
	}

}
