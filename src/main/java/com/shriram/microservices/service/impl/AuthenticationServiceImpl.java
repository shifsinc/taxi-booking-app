package com.shriram.microservices.service.impl;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shriram.microservices.dao.AuthenticationDao;
import com.shriram.microservices.model.authentication.Credentials;
import com.shriram.microservices.service.AuthenticationService;
import com.shriram.microservices.util.DateTimeUtil;

@Service
@Qualifier(value = "authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationDao authenticationDao;
    Logger logger = Logger.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    @Qualifier("domainProperties")
    private Properties domainProperties;

    @Autowired
    @Qualifier("secretProperties")
    private Properties secretProperties;

    @Value("#{domainProperties['token.expirationtime.minutes']}")
    private Integer expirationTime;

    private final static String TIMESTAMP_FORMAT = "datetime.token.format";

    /**
     * Checks whether credentials are valid
     * <p>
     * This method checks whether the user name and password are valid. This method does a one way hash of the password supplied and
     * compares it with the one in DB
     *
     * @return true/false based on whether the credentials are valid or not
     */
    @Override
    public boolean isValidCredentials(Credentials credentials) {
        return authenticationDao.isValidCredentials(credentials);
    }

    /**
     * Creates an encrypted token when a transaction ID is supplied to it
     * <p>
     * This method creates an encrypted token which is a concatenation of transaction ID and the current Time stamp separated by '&', by
     * supplying a key
     *
     * @param credentials
     *            -Credentials supplied
     * @return encrypted token formed using the key/password provided
     */
    @Override
    public String createEncryptedToken(Credentials credentials) {
        String key = secretProperties.getProperty("token.key");
        String timestamp = DateTimeUtil.getFormattedCurrentTime(domainProperties.getProperty(TIMESTAMP_FORMAT));
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(key);
        String encryptedToken = textEncryptor.encrypt(credentials.username() + "&" + timestamp);
        return encryptedToken;
    }

    /**
     * Decrypts and validates the token and also check for expiration of token.
     * <p>
     * This method returns the decrypted username and validates the token and also checks for expiration of token
     *
     * @param encryptedToken
     *            -encrypted token
     * @return username
     */
    @Override
    public String getUsername(String encryptedToken) {
        return decryptToken(encryptedToken);
    }

    /**
     * Decrypts the token given an encrypted token, supplied with the secure key/password used to encrypt/decrypt the token
     * <p>
     * This method Decrypts the token given an encrypted token, supplied with the secure key/password used to encrypt/decrypt the token and
     * also checks for validity of the token, where expiration time has been currently set to 90 minutes
     *
     * @param encryptedToken
     *            -Encrypted token received from the headers
     */
    private String decryptToken(String encryptedToken) {
        String key = secretProperties.getProperty("token.key");
        String[] token = { "" };
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(key);
        try {
            String plainToken = textEncryptor.decrypt(encryptedToken);
            token = plainToken.split("&");
            logger.debug("Timestamp" + token[1]);
            if (hasTokenExpired(token[1])) {
                logger.error("The token has expired");
                return null;
            }
        } catch (EncryptionOperationNotPossibleException exception) {
            return null;
        }
        return token[0];

    }

    /**
     * Checks whether the token has expired or not from timestamp retrieved from the token
     * <p>
     * This method checks for validity of the token, where expiration time has been currently set to 90 minutes from the timestamp set in
     * the token in the Authorization header
     *
     * @param timestamp
     *            -Timestamp retrieved from the account
     */
    private boolean hasTokenExpired(String timestamp) {
        logger.debug("DateTime when token was generated : " + timestamp);
        DateTime tokenDateTime = new DateTime(DateTime.parse(timestamp, DateTimeFormat.forPattern(domainProperties.getProperty(TIMESTAMP_FORMAT))));
        DateTime currentDateTime = new DateTime();
        int timeRemaining = Minutes.minutesBetween(tokenDateTime, currentDateTime).getMinutes();
        logger.debug("Time elapsed since token generated(in min) : " + timeRemaining);
        return (timeRemaining >= expirationTime);
    }

}
