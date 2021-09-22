package io.github.solomkinmv.transactions.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.solomkinmv.transactions.service.audit.AuditWriter;
import io.github.solomkinmv.transactions.service.audit.FileAuditWriter;
import io.github.solomkinmv.transactions.service.encrypt.Base64MockEncryption;
import io.github.solomkinmv.transactions.service.encrypt.EncryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
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

    @Bean
    @ConditionalOnProperty("audit.file") // todo: test autoconfiguration with context test
    public AuditWriter auditWriter(@Value("${audit.file}") String auditFile,
                                   ObjectMapper objectMapper) throws IOException {
        return new FileAuditWriter(auditFile, objectMapper);
    }
}
