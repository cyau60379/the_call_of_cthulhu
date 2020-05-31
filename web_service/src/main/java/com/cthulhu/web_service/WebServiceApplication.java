package com.cthulhu.web_service;

import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebServiceApplication extends SpringBootServletInitializer {
	
	private final String DJANGO_URL = "http://127.0.0.1:8000/web_service/";
	
	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}
	
	// ====================================== Creature research 
	
	@PostMapping(value={"/creatureSearch/creature", "/creatureSearch/author", "/creatureSearch/book", "/creatureSearch/affiliation"}, produces="application/json")
    public ResponseEntity<String> creatureSearch(@RequestBody String jsonString) {
		try {
			return Receiver.getRESTResponse(DJANGO_URL + "creatureSearch", jsonString);
		} catch (NoSuchAlgorithmException e) {
			JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Bad Request: Missing body");
	    	errorResponse.put("status", 400);
	    	return new ResponseEntity<String>(errorResponse.toString(), HttpStatus.BAD_REQUEST);
		}
    }
	
	// ====================================== Author research
	
	@PostMapping(value={"/authorSearch/surname", "/authorSearch/first_name", "/authorSearch/birth", "/authorSearch/death"}, produces="application/json")
    public ResponseEntity<String> authorSearch(@RequestBody String jsonString) {
		try {
			return Receiver.getRESTResponse(DJANGO_URL + "authorSearch", jsonString);
		} catch (NoSuchAlgorithmException e) {
			JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Bad Request: Missing body");
	    	errorResponse.put("status", 400);
	    	return new ResponseEntity<String>(errorResponse.toString(), HttpStatus.BAD_REQUEST);
		}
    }
	
	// ====================================== Book research
	
	@PostMapping(value={"/bookSearch/book", "/bookSearch/author", "/bookSearch/year"}, produces="application/json")
    public ResponseEntity<String> bookSearch(@RequestBody String jsonString) {
		try {
			return Receiver.getRESTResponse(DJANGO_URL + "bookSearch", jsonString);
		} catch (NoSuchAlgorithmException e) {
			JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Bad Request: Missing body");
	    	errorResponse.put("status", 400);
	    	return new ResponseEntity<String>(errorResponse.toString(), HttpStatus.BAD_REQUEST);
		}
    }
	
	// ====================================== Affiliation research
	
	@PostMapping(value={"/affiliationSearch/name", "/affiliationSearch/creature"}, produces="application/json")
    public ResponseEntity<String> affiliationSearch(@RequestBody String jsonString) {
		try {
			return Receiver.getRESTResponse(DJANGO_URL + "affiliationSearch", jsonString);
		} catch (NoSuchAlgorithmException e) {
			JSONObject errorResponse = new JSONObject();
	    	errorResponse.put("error", "Bad Request: Missing body");
	    	errorResponse.put("status", 400);
	    	return new ResponseEntity<String>(errorResponse.toString(), HttpStatus.BAD_REQUEST);
		}
    }
	
}

