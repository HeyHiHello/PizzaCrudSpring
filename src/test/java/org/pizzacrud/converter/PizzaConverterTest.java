package org.pizzacrud.converter;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.pizzacrud.converter.configuration.PizzaConverterTestConfiguration;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.PizzaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(PizzaConverterTestConfiguration.class)
class PizzaConverterTest {
    @Autowired
    private PizzaDtoByIdConverter converter;
    @Autowired
    private Pizza testPizza;
    @Autowired
    private PizzaDto testDto;

    @Test
    void convert() {
        PizzaDto result = converter.convert(1);
        assertEquals(result.getId(), testDto.getId());
        assertEquals(result.getName(), testDto.getName());
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
