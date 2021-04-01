package com.coderman.worldgdp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc //-> This imports the Spring MVC related configuration
@Configuration
@ComponentScan(basePackages = "com.coderman.worldgdp") //-> This is used for declaring the packages that have to be scanned for Spring components(which can be @Configuration, @Service, @Controller, @Component and so on.)
// if no package is defined, it scans starting from the package where the class is defined!
public class AppConfiguration  implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

}

/*
    WebMVCConfigurer
    We are going to implement this interface to override some of the default Spring MVC configuration seen in the previous code.
 */