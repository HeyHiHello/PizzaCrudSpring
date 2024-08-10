package org.pizzacrud.converter;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.pizzacrud.converter.configuration.CustomerConverterTestConfiguration;
import org.pizzacrud.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(CustomerConverterTestConfiguration.class)
class CustomerConverterTest {
    @Autowired
    CustomerDtoByIdConverter converter;

    @Test
    void convert() {
        CustomerDto result = converter.convert(1);
        assertEquals(1, result.id());
    }

    @Test
    void inputTypeTest() {
        assertDoesNotThrow(()->converter.getInputType(TypeFactory.defaultInstance()));
    }

    @Test
    void outputTypeTest() {
        assertDoesNotThrow(()->converter.getOutputType(TypeFactory.defaultInstance()));
    }
}
