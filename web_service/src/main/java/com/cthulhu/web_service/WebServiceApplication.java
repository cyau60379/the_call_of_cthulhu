package com.cthulhu.web_service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class WebServiceApplication {

	private final PrivateKey PRIVATE_KEY = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
	private final PublicKey PUBLIC_KEY_DJANGO = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
	
	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}

	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
    }
	
	@PostMapping("/creatureSearch")
    public String creatureSearch(@RequestBody String jsonString) throws URISyntaxException, NoSuchAlgorithmException {
		return getRESTResponse("http://127.0.0.1:8000/web_service/creatureSearch", jsonString);
    }


	private String getRESTResponse(String url, String jsonString) throws URISyntaxException, NoSuchAlgorithmException{
		RestTemplate template = new RestTemplate();
		String encrypted = Encrypter.encrypt(jsonString, PUBLIC_KEY_DJANGO);
		JSONObject json = new JSONObject();
		json.put("message", encrypted);
		String signature = Encrypter.sign(jsonString, PRIVATE_KEY);
		json.put("signature", signature);
		RequestEntity<String> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON).body(json.toString());
	    String response = "";
	    try{
	    	ResponseEntity<String> responseEntity = template.exchange(request,  String.class);
	        response = responseEntity.getBody();
	    } catch(Exception e){
	    	response = e.getMessage();
	    }
	    JSONObject jsonResponse = new JSONObject(response);
	    String message = jsonResponse.getString("message");
	    String messageSignature = jsonResponse.getString("signature");
	    boolean isVerified = Encrypter.verify(message, messageSignature, PUBLIC_KEY_DJANGO);
	    if(isVerified) {
	    	return Encrypter.decrypt(message, PRIVATE_KEY);
	    } else {
	    	return "Not verified";
	    }
	}
}

