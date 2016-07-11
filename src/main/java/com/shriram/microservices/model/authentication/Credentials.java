package com.shriram.microservices.model.authentication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.shriram.microservices.util.ApplicationContextProviderUtil;

public class Credentials {

    private String username, password;

    private final static String SALT = "token.salt";

    private Logger logger = Logger.getLogger(Credentials.class);

    public Credentials(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String encryptedPassword() {
        String hashedPassword = "";
        String password = null;
        Properties secretProperties = ApplicationContextProviderUtil.getApplicationContext().getBean("secretProperties", Properties.class);

        password = this.password + secretProperties.getProperty(SALT);// adding a salt to the string before it gets hashed.
        logger.debug(secretProperties.getProperty(SALT));
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");// Create MessageDigest object for MD5
            logger.debug("Digest1 " +digest);
            digest.update(password.getBytes(), 0, password.length());// Update input string in message digest
            logger.debug("Digest2 " +digest);
            hashedPassword = new BigInteger(1, digest.digest()).toString(16);// Converts message digest value in base 16 (hex)

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hashedPassword = null;
        }
        return hashedPassword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credentials other = (Credentials) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
