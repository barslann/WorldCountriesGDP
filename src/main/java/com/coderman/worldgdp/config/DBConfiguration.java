package com.coderman.worldgdp.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

//configuration data source object that will connect to the database using the properties defined in the application.properties file

@Configuration // -> this is to indicate to Spring Framework that this class creates Java objects that contain some configuration
public class DBConfiguration {
    //@Value -> It is used to refer to the properties defined in the application.properties,
    // which are resolved by the PropertySourcesPlaceHolderConfigurer bean defined in the PropertiesWithJavaConfig class.
    @Value("${jdbcUrl}") String jdbcUrl;
    @Value("${dataSource.user}") String username;
    @Value("${dataSource.password}") String password;
    @Value("${dataSourceClassName}") String className;

    @Bean //-> This is method-level annotation, used to indicate to Spring Framework that the method returns Java objects
    //whose life cycle is managed by Springframework and injected into places where its dependency is declared
    public DataSource getDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(className);
        return ds;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        return namedParameterJdbcTemplate;
    }
}
