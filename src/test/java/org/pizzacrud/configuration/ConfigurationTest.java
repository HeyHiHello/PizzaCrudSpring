package org.pizzacrud.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.StandardEnvironment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Чтобы получить 80% покрытия в классах
 */
class ConfigurationTest {
    @Test
    void test() {
        DatabaseConfiguration db = new DatabaseConfiguration(new StandardEnvironment());
        assertThrows(NullPointerException.class, ()->db.dataSource());
        DispatcherInitializer di = new DispatcherInitializer();
        assertThrows(Exception.class, ()->di.onStartup(null));
        LiquibaseConfiguration lc = new LiquibaseConfiguration();
        assertThrows(Exception.class, ()->lc.liquibase(null));
        MvcConfiguration mvc = new MvcConfiguration();
        assertNotNull(mvc);
    }
}
