package com.example.rest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.NewJiraApplication;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NewJiraApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class UserControllerTests {
    @Autowired
    UserRepository userRepository;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        RestAssured.port = port;
    }

    @Test
    public void canDisableEnabledUserTest() {
		User user = new User();
		user.setFirstName("testFirstName");
		user.setLastName("testLastName");
		user.setLogin("testLogin");
		user.setPassword("testPassword");
		user.setStatus("active");
		userRepository.save(user);
		
		Long addedUserId = user.getId();
		String url = "/api/user/disable/" + addedUserId;
		
		RestAssured.when().
			get(url)
		.then()
			.statusCode(200)
			.body("id", Matchers.equalTo(addedUserId.intValue()))
			.body("status", Matchers.equalTo("disabled"));
		
		String response = RestAssured.get(url).asString();
		System.out.println(response);
    }
    
    @Test
    public void disabledUserDisplays404CodeTest() {
		
		Long missedUserId = 1L;
		String url = "/api/user/disable/" + missedUserId;
		
		RestAssured.when().
			get(url)
		.then()
			.statusCode(404).extract();
		
		String response = RestAssured.get(url).asString();
		System.out.println(response);
    }    
}
