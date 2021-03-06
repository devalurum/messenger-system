package org.urumov.messengersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class MessengerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerSystemApplication.class, args);
    }

}
