package com.cthulhu.web_service;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebServiceApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}
	
	@GetMapping("/creatureSearch/creature")
    public String creatureSearch(@RequestParam(value = "name", defaultValue = "Cthulhu") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/creatureSearch", name, "creature");
    }
	
	@GetMapping("/creatureSearch/author")
    public String creatureSearchAuthor(@RequestParam(value = "name", defaultValue = "Lovecraft") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/creatureSearch", name, "author");
    }
	
	@GetMapping("/creatureSearch/book")
    public String creatureSearchBook(@RequestParam(value = "name", defaultValue = "The Call of Cthulhu") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/creatureSearch", name, "book");
    }
	
	@GetMapping("/creatureSearch/affiliation")
    public String creatureSearchAffiliation(@RequestParam(value = "name", defaultValue = "Outer Gods") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/creatureSearch", name, "affiliation");
    }
	
	@GetMapping("/authorSearch/surname")
    public String authorSearchSurname(@RequestParam(value = "name", defaultValue = "Lovecraft") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/authorSearch", name, "surname");
    }
	
	@GetMapping("/authorSearch/first_name")
    public String authorSearchFirstName(@RequestParam(value = "name", defaultValue = "Howard Phillips") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/authorSearch", name, "first_name");
    }
	
	@GetMapping("/authorSearch/birth")
    public String authorSearchBirth(@RequestParam(value = "name", defaultValue = "1890") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/authorSearch", name, "birth");
    }
	
	@GetMapping("/authorSearch/death")
    public String authorSearchDeath(@RequestParam(value = "name", defaultValue = "1937") String name) throws URISyntaxException, NoSuchAlgorithmException {
		return Receiver.getRESTResponse("http://127.0.0.1:8000/web_service/authorSearch", name, "death");
    }
}

