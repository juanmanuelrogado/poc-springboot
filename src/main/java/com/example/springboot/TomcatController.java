package com.example.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

	@PostMapping("/add-site")
	public HttpStatus addOpportunitySite(@RequestHeader Map<String, String> headers, @RequestBody String body) throws Exception {
		System.out.println("POST");
	    headers.forEach((key, value) -> {
	        System.out.println(String.format("Header '%s' = %s", key, value));
	    });
	    
	    System.out.println("Body: " + body);

	    //addWebsite(); // Just a PoC; we will have to retrieve the Opportunity Name from the body as its name
	    getWebSites(); // for now, just testing we can invoke a Liferay API
	    
	    return HttpStatus.OK;
	}

    private static String getWebSites() throws Exception 
    {
    	String result = "";
	    try
	    {
		    HttpClient client = new DefaultHttpClient();
	    	HttpGet request = new HttpGet("http://localhost:9080/api/jsonws/group/get-groups/company-id/20097/parent-group-id/0/site/true");
	    	
	    	// We will probably need some Oauth2 token here
	    	request.addHeader("Authorization", "Basic dGVzdEBsaWZlcmF5LmNvbTphYmMxMjM=");
	    	
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
    
    
    private static String addWebSite() throws ClientProtocolException, IOException {
    	String result = "";
    	
    	//String url = "http://localhost:9080/api/jsonws/group/add-group/parent-group-id/0/live-group-id/0/name-map/%7B%22en_US%22%3A%22ffffff%22%7D/description-map/%7B%22en_US%22%3A%ffffff%22%7D/type/3/manual-membership/true/membership-restriction/0/friendly-url/%2Fffffff/site/true/active/true";
    	String url = "http://localhost:9080/api/jsonws/group/add-group";
    	
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	post.addHeader("Authorization", "Basic dGVzdEBsaWZlcmF5LmNvbTphYmMxMjM=");
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("parentGroupId", "0"));
    	params.add(new BasicNameValuePair("liveGroupId", "0"));
    	params.add(new BasicNameValuePair("nameMap", "{\"en_US\":\"kkkkkkkk\"}"));
    	params.add(new BasicNameValuePair("descriptionMap", "{\"en_US\":\"kkkkkkkk\"}"));
    	
    	
    	StringEntity input = new StringEntity("{'parentGroupId': '0','liveGroupId': '0','nameMap': 'aaaa','descriptionMap': 'aaaa','type': '3','manualMembership': 'true','membershipRestriction': '0','friendlyUrl': '/aaaaa','site': 'true','active': 'true'}"); //here instead of JSON you can also have XML
    	input.setContentType("application/json");
    	
    	post.setEntity(input);
    	HttpResponse response = client.execute(post);
    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    	String line = "";
    	while ((line = rd.readLine()) != null) {
    		System.out.println(line);
    		result+= line;
    	}
    	
    	return result;
    	
    	
    }
    
}