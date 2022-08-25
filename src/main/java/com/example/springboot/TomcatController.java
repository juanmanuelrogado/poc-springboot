package com.example.springboot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TomcatController {

	@GetMapping("/hello")
	public String sayHello() throws Exception {
	    System.out.println("hello springBoot");
	    return "Hello, SpringBoot!";
	}

	@GetMapping("/get-users")
	public String getUsers(@RequestHeader Map<String, String> headers) throws Exception {
	    System.out.println("**** GETTING USERS ****");
	       
		String usersJson = callGetUsers(headers.get("authorization"));
		
		return usersJson;
	}

	@PostMapping("/some-post-api")
	public HttpStatus somePostApi(@RequestHeader Map<String, String> headers, @RequestBody String body) throws Exception {
		headers.forEach((key, value) -> {
	        System.out.println(String.format("Header '%s' = %s", key, value));
	    });
	    
	    System.out.println("Body: " + body);

	    // TODO: call LR API
	    
	    return HttpStatus.OK;
	}

	private static String callGetUsers(String bearer) throws Exception 
    {
		System.out.println("*** CALLING API GETUSERS ***");
    	String result = "";
		
	    try
	    {
		    HttpClient client = new DefaultHttpClient();
	    	HttpGet request = new HttpGet("http://localhost:8080/o/headless-admin-user/v1.0/user-accounts");
	    	
	    	// Propagate OAuth2 access token
	    	request.addHeader("Authorization", bearer);
	    	
	    	HttpResponse response = (HttpResponse) client.execute(request);
	    	BufferedReader rd = new BufferedReader (new InputStreamReader(((HttpResponse) response).getEntity().getContent()));
	    	String line = "";
	    	while ((line = rd.readLine()) != null) {
	    		System.out.println(line);
	    		result+= line;
	    	}
	    }
	    finally
	    {
	    	
	    }
	    return result;
    }
}