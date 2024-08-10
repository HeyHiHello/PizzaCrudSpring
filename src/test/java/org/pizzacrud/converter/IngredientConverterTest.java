package org.pizzacrud.converter;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.pizzacrud.converter.configuration.IngredientConverterTestConfiguration;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(IngredientConverterTestConfiguration.class)
class IngredientConverterTest {
    @Autowired
    private IngredientDtoByIdConverter converter;
    @Autowired
    private Ingredient testIngredient;

    @Test
    void convert() {
        IngredientDto ingredientDto = converter.convert(1);
        assertEquals(testIngredient.getId(), ingredientDto.id());
        assertEquals(testIngredient.getName(), ingredientDto.name());
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
