package com.cthulhu.web_service;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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
    public String creatureSearch(@RequestBody String jsonString) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse(DJANGO_URL + "creatureSearch", jsonString);
    }
	
	// ====================================== Author research
	
	@PostMapping(value={"/authorSearch/surname", "/authorSearch/first_name", "/authorSearch/birth", "/authorSearch/death"}, produces="application/json")
    public String authorSearchSurname(@RequestBody String jsonString) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse(DJANGO_URL + "authorSearch", jsonString);
    }
	
	// ====================================== Book research
	
	@PostMapping(value={"/bookSearch/book", "/bookSearch/author", "/bookSearch/year"}, produces="application/json")
    public String bookSearch(@RequestBody String jsonString) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse(DJANGO_URL + "bookSearch", jsonString);
    }
	
	// ====================================== Affiliation research
	
	@PostMapping(value={"/affiliationSearch/name", "/affiliationSearch/creature"}, produces="application/json")
    public String affiliationSearch(@RequestBody String jsonString) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse(DJANGO_URL + "affiliationSearch", jsonString);
    }
	
}

