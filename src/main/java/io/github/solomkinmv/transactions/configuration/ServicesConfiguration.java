package io.github.solomkinmv.transactions.configuration;

import io.github.solomkinmv.transactions.service.encrypt.Base64MockEncryption;
import io.github.solomkinmv.transactions.service.encrypt.EncryptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ServicesConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    @ConditionalOnMissingBean(EncryptionService.class) // todo: test autoconfiguration with context test
    public EncryptionService encryptionService() {
        return new Base64MockEncryption();
    }
}
