package javaSalesProject;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnit_isValid_Test2 {
	
	public Auction auction;

	@BeforeEach
	void setUp() throws Exception {
		Driver.init();
		Item item = new Item(100, "item1", 10);	// New item with 
		auction = new Auction(item, 100, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20));
		Customer cust1 = new Customer("Joe", "ilovepie333", "customer");
		auction.process(new Bid(125, auction, cust1));
		Customer cust2 = new Customer("Autumn", "basil2020", "customer");
		auction.process(new Bid(150, auction, cust2));
	}

	@Test
	void test() {
		// Current sales price is now $125
		// Next bid should be valid if it is greater than or equal to $135
		Customer cust3 = new Customer("Bob", "northmont1984", "customer");
		Bid testBid = new Bid(135, auction, cust3, LocalDateTime.now());
		// The bid of $135 should be valid and return true
		boolean result = auction.isValid(testBid);
		
		if(!result) {
			fail("The bid should have been deemed valid");
		}
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}


}
