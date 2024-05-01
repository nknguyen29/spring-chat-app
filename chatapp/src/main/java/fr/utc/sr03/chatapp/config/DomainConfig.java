package fr.utc.sr03.chatapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("fr.utc.sr03.chatapp.domain")
@EnableJpaRepositories("fr.utc.sr03.chatapp.repos")
@EnableTransactionManagement
public class DomainConfig {
}
