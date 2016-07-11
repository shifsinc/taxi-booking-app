package com.shriram.microservices.service;

import com.shriram.microservices.model.authentication.Credentials;

public interface AuthenticationService {

    /**
     * Checks whether credentials are valid
     * <p>
     * This method checks whether the user name and password are valid. This method does a one way hash of the password supplied and
     * compares it with the one in DB
     *
     * @return true/false based on whether the credentials are valid or not
     */
    public boolean isValidCredentials(Credentials credentials);

    /**
     * Creates an encrypted token when a transaction ID is supplied to it
     * <p>
     * This method creates an encrypted token which is a concatenation of username and the current Time stamp separated by '&', by supplying
     * a key
     *
     * @param credentials
     *            -Credentials supplied
     * @return encrypted token formed using the key/password provided
     */
    public String createEncryptedToken(Credentials credentials);

    /**
     * Decrypts and validates the username and also check for expiration of token.
     * <p>
     * This method returns the decrypted username and validates the token and also checks for expiration of token
     *
     * @param encryptedToken
     *            -encrypted token
     * @return username
     */
    public String getUsername(String encryptedToken);

}
