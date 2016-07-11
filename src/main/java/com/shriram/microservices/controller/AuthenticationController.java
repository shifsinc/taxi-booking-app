package com.shriram.microservices.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shriram.microservices.model.authentication.Credentials;
import com.shriram.microservices.service.AuthenticationService;
import com.shriram.microservices.util.ResponseUtil;
import com.shriram.microservices.valueObject.CredentialsValueObject;

/**
 * Authentication Controller for generating token which are used for subsequent API calls
 * <p>
 * This controller generates token after validating username and password which are used for subsequent API calls. The subsequent calls
 * should have the token in the header with the name 'Authorization'
 *
 * @method - createToken - for creating token
 */
@RestController
public class AuthenticationController {

    @Autowired
    @Qualifier("authenticationService")
    private AuthenticationService authenticationService;

    Logger logger = Logger.getLogger(AuthenticationController.class);

    /**
     * Generates token,which serves as API authorization token
     * <p>
     * This method generates token which are used for subsequent API calls. The subsequent calls should have the token in the header with
     * the name 'Authorization'
     *
     * url - /login
     *
     * @param request
     *            - HttpServletRequest
     * @param response
     *            - HttpServletResponse
     * @param credentialsValueObject
     *            - CredentialsValueObject
     * @return {token: "ahfjk00909"}
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public Map<String, Object> createToken(@RequestBody CredentialsValueObject credentialsValueObject, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> responseContent = new HashMap<String, Object>();
        String password;
        if (StringUtils.isEmpty(credentialsValueObject.getUsername()) || StringUtils.isEmpty(credentialsValueObject.getPassword())) {
            return ResponseUtil.unauthorizedMessage(response, "missing credentials");
        }
        try {
            password = new String(Base64.decodeBase64(credentialsValueObject.getPassword()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.info("Password can't be decoded");
            return ResponseUtil.unauthorizedMessage(response, "wrong credentials");
        }
        Credentials credentials = new Credentials(credentialsValueObject.getUsername(), password);

        if (authenticationService.isValidCredentials(credentials)) {
            responseContent.put("token", authenticationService.createEncryptedToken(credentials));
        } else {
            return ResponseUtil.unauthorizedMessage(response, "wrong credentials");
        }

        return responseContent;
    }

}
