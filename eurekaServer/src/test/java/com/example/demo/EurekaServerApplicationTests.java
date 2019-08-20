package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patrick.eureka.EurekaServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaServerApplication.class)
public class EurekaServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
