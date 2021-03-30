package com.coderman.worldgdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


// Define properties resolver that will be able to resolve the properties when used from within the code.
// This class reads all the properties from application.properties file stored in classpath(src/main/resources)
@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesWithJavaConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
