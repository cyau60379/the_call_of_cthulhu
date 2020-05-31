package com.cthulhu.web_service;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ReceiverTests {

	@Test
	void testGetRestResponseWithWrongURL_ShouldThrowException() throws JSONException {
		try {
			ResponseEntity<String> response = Receiver.getRESTResponse("fdfsfsfs9+8f+8sdf8+sd8f98fs", "{\"test\":\"test\"}");
	    	JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Service Unavailable");
	    	errorResponse.put("status", 503);
	    	ResponseEntity<String> re = new ResponseEntity<String>(errorResponse.toString(), HttpStatus.SERVICE_UNAVAILABLE);
			assertEquals(re, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}	
	}
	
	@Test
	void testGetRestResponse() throws JSONException {
		try {
			ResponseEntity<String> response = Receiver.getRESTResponse("http://localhost:8000/web_service/creatureSearch", "{\"name\":\"Cthulhu\", \"searchType\":\"creature\"}");
			assertEquals(HttpStatus.OK, response.getStatusCode());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}	
	}
	
	@Test
	void testGetRestResponseNoBody() throws JSONException {
		try {
			ResponseEntity<String> response = Receiver.getRESTResponse("http://localhost:8000/web_service/creatureSearch", null);
			JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Service Unavailable");
	    	errorResponse.put("status", 503);
	    	ResponseEntity<String> re = new ResponseEntity<String>(errorResponse.toString(), HttpStatus.SERVICE_UNAVAILABLE);
	    	assertEquals(re, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}	
	}
	
	@Test
	void testGetRestResponseURISythaxException() throws JSONException {
		try {
			ResponseEntity<String> response = Receiver.getRESTResponse("http://localhost:8000/web_service/cre¨^§?%ù*£$¤atureSearch", null);
			JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Not Found");
	    	errorResponse.put("status", 404);
	    	ResponseEntity<String> re = new ResponseEntity<String>(errorResponse.toString(), HttpStatus.NOT_FOUND);
	    	assertEquals(re, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}	
	}
}
