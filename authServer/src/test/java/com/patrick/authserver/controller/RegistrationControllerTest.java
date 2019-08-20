package com.patrick.authserver.controller;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patrick.authserver.AuthServerApplication;
import com.patrick.authserver.auth.beans.RegistrationRequest;
import com.patrick.authserver.controllers.RegistrationController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApplication.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest {

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
	
	private static final String COUNT_QUERY = "Select count(*) from Users";
	
	@LocalServerPort
    private int port;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RegistrationController registrationController;
	
	@Ignore
	@Test
	public final void testRegistrationController() {
		RegistrationRequest registrationReq = new RegistrationRequest("lindsey17", "blessed", "lindseyK@gmail.com", "Lindsey", "Knodel");
		long beforeCount = jdbcTemplate.queryForObject(COUNT_QUERY, Long.class);
		registrationController.registerNewUser(registrationReq);
		long afterCount = jdbcTemplate.queryForObject(COUNT_QUERY, Long.class);
		assertEquals(++beforeCount, afterCount);  
	}

	
	@Configuration
	@EnableAutoConfiguration
	@EnableEurekaServer
	static class EurekaServer {
		
	}
}

