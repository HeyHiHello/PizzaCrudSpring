package org.pizzacrud.database;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;

public class TestDbInitializer {
    public static MySQLContainer buildMySQLContainer() {
        return new MySQLContainer("mysql:8.0")
                .withDatabaseName("PizzaAppDB")
                .withUsername("root")
                .withPassword("root");
    }

    public static void registerPgProperties(DynamicPropertyRegistry registry, MySQLContainer mySQLContainer) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
}
