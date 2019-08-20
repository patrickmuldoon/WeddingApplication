package com.patrick.authserver.proxy;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:testing.properties")
public class AuthorizationTest {
	
    @BeforeClass
    public static void init() throws JSONException {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        loginJsonObject = new JSONObject();
        loginJsonObject.put("username", "lindsey17");
        loginJsonObject.put("password", "blessed");
    }
    
    @AfterClass
    public static void cleanUp() {
    	headers = null;
    }
    
    @LocalServerPort
    private int port;
    
    private static HttpHeaders headers;
    private static JSONObject loginJsonObject;
    //private final ObjectMapper objectMapper = new ObjectMapper();

	
	
	private static final String COUNT_QUERY = "Select count(*) from Users";
    
	@Test
    public final void testLogin() {
    	TestRestTemplate testRestTemplate = new TestRestTemplate();
    	HttpEntity<String> request = new HttpEntity<>(loginJsonObject.toString(), headers);
    	ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:"+port+"/auth/", request, String.class);
    	System.out.println(response);
    }

}
