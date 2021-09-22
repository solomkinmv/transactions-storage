package io.github.solomkinmv.transactions.persistence.configuration;

import io.github.solomkinmv.transactions.persistence.InMemoryTransactionsRepository;
import io.github.solomkinmv.transactions.persistence.TransactionsRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    @ConditionalOnMissingBean(TransactionsRepository.class) // todo: test autoconfiguration with context test
    public InMemoryTransactionsRepository inMemoryTransactionsRepository() {
        return new InMemoryTransactionsRepository();
    }
}
