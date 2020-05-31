package com.cthulhu.web_service;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ReceiverTests {

	@Test
	void testGetRestResponseWithWrongURL_ShouldThrowException() {
		try {
			ResponseEntity<String> response = Receiver.getRESTResponse("fdfsfsfs9+8f+8sdf8+sd8f98fs", "{\"test\":\"test\"}");
			assertEquals(null, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}
		
	}
}
