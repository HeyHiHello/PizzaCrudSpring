package org.pizzacrud.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatabaseConfiguration.class)
public class LiquibaseConfiguration {
    @Bean
    public SpringLiquibase liquibase(DatabaseConfiguration databaseConfiguration) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/main-changelog.xml");
        liquibase.setDataSource(databaseConfiguration.dataSource());
        return liquibase;
    }
}
