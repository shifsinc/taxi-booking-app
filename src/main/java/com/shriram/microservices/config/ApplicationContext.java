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

    @Value("#{envProperties['jndi.dataSource.name']}")
    private String jndiName;

    @Bean(name = "envProperties")
    public PropertiesFactoryBean envPropertiesMapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource("/srv/microservices/config/" + System.getProperty("profile") + "/env.properties"));
        return bean;
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup dataSource = new JndiDataSourceLookup();
        dataSource.setResourceRef(true);
        return dataSource.getDataSource(jndiName);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource());
        return template;
    }

    @Bean(name = "applicationContextProviderUtil")
    public ApplicationContextProviderUtil applicationContextProviderUtil() {
        return new ApplicationContextProviderUtil();
    }

}
