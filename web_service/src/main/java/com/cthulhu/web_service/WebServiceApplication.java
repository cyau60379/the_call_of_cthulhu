package com.cthulhu.web_service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
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

	private final String PRIVATE_KEY = "src\\main\\resources\\static\\private_key_Spring.der";
	private final String PUBLIC_KEY_DJANGO = "src\\main\\resources\\static\\public_key_Django.der";
	
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
	
	/*
	@RequestMapping( value= "/controller3", method = RequestMethod.POST)
	 public @ResponseBody void process(@RequestBody String jsonString){

	    //retrieve jsonString and transform it to retrieve all data
		String request = "requestType"; //Get the request type from request
	    String url = "";
	    MultiValueMap<String, String> params= null;
	    switch(request){
	         case "request1": //how to call REST API 1?
	            url = "/controller1";
	            params = ; //Get the parameter map from request 
	         case "request2": //how to call REST API 2?
	            url = "/controller2";
	            params = request2Param; //Get the parameter map from request 
	      }

	      //Now call the method with parameters
	      getRESTResponse(url, params);

	  }
*/

	private String getRESTResponse(String url, String jsonString) throws URISyntaxException, NoSuchAlgorithmException{
		RestTemplate template = new RestTemplate();
		String encrypted = Encrypter.encrypt(jsonString, KeyLoader.getPublicKey(PUBLIC_KEY_DJANGO));
		/*
		JSONObject json = new JSONObject();
		json.append("message", encrypted);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashedJson = digest.digest(jsonString.getBytes(StandardCharsets.UTF_8));
		String signature = Encrypter.encrypt(hashedJson.toString(), KeyLoader.getPrivateKey(PRIVATE_KEY));
		json.append("signature", signature);
		*/
		RequestEntity<String> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON).body(encrypted);
	    String response = "";
	    try{
	    	ResponseEntity<String> responseEntity = template.exchange(request,  String.class);
	        response = responseEntity.getBody();
	    } catch(Exception e){
	    	response = e.getMessage();
	    }
	    return response;
	}
}

