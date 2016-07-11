package com.shriram.microservices.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shriram.microservices.dao.AuthenticationDao;
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
@Repository
public class AuthenticationDaoImpl implements AuthenticationDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("sqlQueries")
    private Properties sqlQueries;

    @Autowired
    @Qualifier("domainProperties")
    private Properties domainProperties;

    Logger logger = Logger.getLogger(AuthenticationDao.class);

    /**
     * Checked whether credentials are valid or not
     * <p>
     * This method compares the credentials with those in the DB and returns true or false based on their existence
     *
     */
    public boolean isValidCredentials(Credentials hashedCredentials) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        String credentialsValiditonQuery = sqlQueries.getProperty("authentication.credentials.validation");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", hashedCredentials.username());
        paramMap.put("password", hashedCredentials.encryptedPassword());
        logger.debug("Password :"+hashedCredentials.encryptedPassword());
        return template.queryForObject(credentialsValiditonQuery, paramMap, Integer.class) > 0 ? true : false;
    }

}
