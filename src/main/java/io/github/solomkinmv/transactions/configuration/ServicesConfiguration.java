package io.github.solomkinmv.transactions.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ServicesConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
