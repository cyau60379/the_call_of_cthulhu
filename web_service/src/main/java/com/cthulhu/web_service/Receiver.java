package com.cthulhu.web_service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Receiver {
	
	private final static PrivateKey PRIVATE_KEY = KeyLoader.getPrivateKey("src\\main\\resources\\static\\private_key_Spring.der");
	private final static PublicKey PUBLIC_KEY_DJANGO = KeyLoader.getPublicKey("src\\main\\resources\\static\\public_key_Django.der");
	
	/**
	 * Function which encrypts, signs and sends the request to the other web service
	 * and retrieves the response, verifies signature, decrypts and return the response
	 * @param url: the URL to send the JSON request
	 * @param jsonString: the JSON to send
	 * @return: the decrypted response message
	 * @throws URISyntaxException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getRESTResponse(String url, String name, String type) throws URISyntaxException, NoSuchAlgorithmException{
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("name", name);
		jsonRequest.put("searchType", type);
		String jsonString = jsonRequest.toString();
		
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
	    JSONArray returningResponse = new JSONArray();
	    if(isVerified) {  //if they are all verified
	    	System.out.println("Verified");
		    String reception = "";
	    	for(int i = 0; i < message.length(); i++) {
		    	reception += Encrypter.decrypt(message.getString(i), PRIVATE_KEY);
		    }
	    	System.out.println();
	    	returningResponse = new JSONArray(reception);
	    } else {
	    	JSONObject content = new JSONObject();
	    	content.put("message", "Not verified");
	    	System.out.println("Not verified");
	    	returningResponse.put(content);
	    }
	    return returningResponse.toString();
	}
}
