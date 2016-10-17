package com.shriram.microservices.config;

import com.shriram.microservices.util.ApplicationContextProviderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
public class ApplicationContext {

    @Bean(name = "applicationContextProviderUtil")
    public ApplicationContextProviderUtil applicationContextProviderUtil() {
        return new ApplicationContextProviderUtil();
    }

}
