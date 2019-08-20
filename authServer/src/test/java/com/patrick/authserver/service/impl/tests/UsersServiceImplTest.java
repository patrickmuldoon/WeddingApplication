package com.patrick.authserver.service.impl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patrick.authserver.AuthServerApplication;
import com.patrick.authserver.auth.beans.RegistrationRequest;
import com.patrick.authserver.auth.beans.Users;
import com.patrick.authserver.service.UsersService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApplication.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersServiceImplTest {
	
	static ConfigurableApplicationContext eurekaServer;

    @BeforeClass
    public static void startEureka() {
        eurekaServer = SpringApplication.run(EurekaServer.class,
                "--server.port=8761",
                "--eureka.client.register-with-eureka=false",
                "--eureka.client.fetch-registry=false");
    }

    @AfterClass
    public static void closeEureka() {
        eurekaServer.close();
    }
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String COUNT_QUERY = "Select count(*) from Users";
	
	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldRegisterClientInEurekaServer() throws InterruptedException {
        // registration has to take place...
        Thread.sleep(3000);
        
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("http://localhost:8761/eurkea/AUTH-SERVICE", String.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.FOUND));
        
    }
	
	@Ignore
	@Test
	public void testSaveNewUser() {
		RegistrationRequest request = new RegistrationRequest("pmuldoon2020", "password", "pmuldoon20@yahoo.com", "Patrick", "Muldoon");
		Users user = new Users(request);
		long rowCount = jdbcTemplate.queryForObject(COUNT_QUERY, Long.class);
		userService.saveUser(user);
		long afterCount = jdbcTemplate.queryForObject(COUNT_QUERY, Long.class);
		assertEquals(++rowCount, afterCount);
	}
	
	@Ignore
	@Test
	public final void testDeleteUser() {
		RegistrationRequest request = new RegistrationRequest("pmuldoon2020", "password", "pmuldoon20@yahoo.com", "Patrick", "Muldoon");
		Users user = new Users(request);
		long rowCount = jdbcTemplate.queryForObject(COUNT_QUERY, Long.class);
		user = userService.findByUsername(user.getUsername());
		userService.deleteUser(user.getId());
		long afterCount = jdbcTemplate.queryForObject(COUNT_QUERY, Long.class);
		assertEquals(rowCount, ++afterCount);
	}
	
	@Configuration
	@EnableAutoConfiguration
	@EnableEurekaServer
	static class EurekaServer {
		
	}
}
