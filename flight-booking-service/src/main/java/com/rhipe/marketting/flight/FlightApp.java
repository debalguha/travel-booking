package com.rhipe.marketting.flight;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class FlightApp {
    public static void main( String[] args ) {
        SpringApplication.run(FlightApp.class, args);
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet(), "/api/*");
        servlet.setName("CamelServlet");
        return servlet;
    }
}
