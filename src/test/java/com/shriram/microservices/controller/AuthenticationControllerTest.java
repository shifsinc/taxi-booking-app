package com.shriram.microservices.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.model.authentication.Credentials;
import com.shriram.microservices.service.AuthenticationService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MicroservicesServletTest.class)
public class AuthenticationControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    @Autowired
    private AuthenticationController reservationController;

    @Mock
    private AuthenticationService authenticationService;

    private MockMvc mockMvc;

    private static final String VALID_TOKEN = "nkSZa0A+4jNek/Ba4o1qdw6TrWmtAnQ0N29TEEjRa5HasSwHofNQ6ZzO5S4futg2";
    private final static String LOGIN_URL = "/login";
    private final static String TOKEN_KEY = "$.token";
    private final static String REQUEST_JSON = "{      \"username\": \"user1\",      \"password\": \"kldjfadf8909\"}";
    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void createTokenTest() throws Exception {
        when(authenticationService.isValidCredentials(Mockito.any(Credentials.class))).thenReturn(true);
        when(authenticationService.createEncryptedToken(Mockito.any(Credentials.class))).thenReturn(VALID_TOKEN);
        this.mockMvc.perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON).content(REQUEST_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath(TOKEN_KEY).value(VALID_TOKEN));
    }

}
