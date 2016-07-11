package com.shriram.microservices.service;

import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.dao.AuthenticationDao;
import com.shriram.microservices.model.authentication.Credentials;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MicroservicesServletTest.class)
public class AuthenticationServiceTest extends TestCase {

    @InjectMocks
    @Autowired
    private AuthenticationService authenticationService;

    @Mock
    AuthenticationDao authenticationDao;

    private final static Credentials credentials = new Credentials("user1", "aGVsbG9AMTIz");

    Logger logger = Logger.getLogger(AuthenticationServiceTest.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validCredentialsTest() {
        when(authenticationDao.isValidCredentials(Mockito.any(Credentials.class))).thenReturn(true);
        assertTrue(authenticationService.isValidCredentials(credentials));
    }

    @Test
    public void invalidCredentialsTest() {
        when(authenticationDao.isValidCredentials(Mockito.any(Credentials.class))).thenReturn(false);
        assertFalse(authenticationService.isValidCredentials(credentials));
    }

}
