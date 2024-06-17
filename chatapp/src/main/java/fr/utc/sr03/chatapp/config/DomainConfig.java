package fr.utc.sr03.chatapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * This class is used to configure the domain package
 * It is used to scan the domain package for entities and repositories
 * It is used to enable the transaction management
 */
@Configuration
@EntityScan("fr.utc.sr03.chatapp.domain")
@EnableJpaRepositories("fr.utc.sr03.chatapp.repos")
@EnableJpaAuditing
@EnableTransactionManagement
public class DomainConfig {
}
