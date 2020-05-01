package com.cthulhu.web_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Controller
public class WebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}

	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
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
	  private String getRESTResponse(String url, MultiValueMap<String, String> params){
	     RestTemplate template = new RestTemplate();
	     HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params);
	    String response = "";
	     try{
	        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, requestEntity,  String.class);
	        response = responseEntity.getBody();
	    }
	    catch(Exception e){
	        response = e.getMessage();
	    }
	    return response;
	  }
}

