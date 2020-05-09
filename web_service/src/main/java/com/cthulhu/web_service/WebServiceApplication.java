package com.cthulhu.web_service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.json.JSONArray;
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


	/**
	 * Function which encrypts, signs and sends the request to the other web service
	 * and retrieves the response, verifies signature, decrypts and return the response
	 * @param url: the URL to send the JSON request
	 * @param jsonString: the JSON to send
	 * @return: the decrypted response message
	 * @throws URISyntaxException
	 * @throws NoSuchAlgorithmException
	 */
	private String getRESTResponse(String url, String jsonString) throws URISyntaxException, NoSuchAlgorithmException{
		RestTemplate template = new RestTemplate();  //the template of a HTTP request
		
		String encrypted = Encrypter.encrypt(jsonString, PUBLIC_KEY_DJANGO);   //encrytion of the message
		String signature = Encrypter.sign(jsonString, PRIVATE_KEY);
		
		JSONObject json = new JSONObject();  //JSON to be sent
		json.put("message", encrypted);  //add the encrypted message to the JSON
		json.put("signature", signature);  //add the signature to the JSON
		
		RequestEntity<String> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON).body(json.toString());
		//create the request which will be sent
	    
		String response = "";
	    try{
	    	ResponseEntity<String> responseEntity = template.exchange(request,  String.class);
	        response = responseEntity.getBody();  //retrieve the content of the response
	        System.out.println("Reponse received");
	    } catch(Exception e){
	    	System.out.println("Failure to get response");
	    	response = e.getMessage();
	    }
	    
	    JSONObject jsonResponse = new JSONObject(response);  //transform string to JSON
	    JSONArray message = jsonResponse.getJSONArray("message");
	    JSONArray messageSignature = jsonResponse.getJSONArray("signature");
	    boolean isVerified = true;
	    //verification of each element from the JSON
	    for(int i = 0; i < message.length(); i++) {
	    	isVerified &= Encrypter.verify(message.getString(i), messageSignature.getString(i), PUBLIC_KEY_DJANGO);
	    }
	    if(isVerified) {  //if they are all verified
	    	System.out.println("Verified");
	    	JSONArray returningResponse = new JSONArray();
		    for(int i = 0; i < message.length(); i++) {
		    	returningResponse.put(new JSONObject(Encrypter.decrypt(message.getString(i), PRIVATE_KEY)));
		    }
		    return returningResponse.toString();
	    } else {
	    	System.out.println("Not verified");
	    	return "Not verified";
	    }
	}
}

