package com.example.server;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.sse.SseHttpTransportFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {
    @Bean
    public Server rsServer(Bus bus, StatsRestService service) {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/");
        endpoint.setServiceBean(service);
        endpoint.setTransportId(SseHttpTransportFactory.TRANSPORT_ID);
        endpoint.setProvider(new JacksonJsonProvider());
        return endpoint.create();
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/static/**")
          .addResourceLocations("classpath:/web-ui/"); 
    }
}
