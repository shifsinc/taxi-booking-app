package com.shriram.microservices.dao;

import com.shriram.microservices.model.authentication.Credentials;

/**
 * Data access object talking to DB
 * <p>
 * This talks to the DB and performs DB queries and returns data
 *
 * @param dataSource
 *            - DB data source
 * @param sqlQueries
 *            - List of SQL queries that are performed on DB
 */
public interface AuthenticationDao {

    /**
     * Checked whether credentials are valid or not
     * <p>
     * This method compares the credentials with those in the DB and returns true or false based on their existence
     *
     */
    public boolean isValidCredentials(Credentials hashedCredentials);

}
