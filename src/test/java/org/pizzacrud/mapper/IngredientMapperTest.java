package org.pizzacrud.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientMapperTest {
    private static IngredientDto ingredientDto;
    private static Ingredient ingredient;
    private static IngredientMapper ingredientMapper;

    @BeforeAll
    public static void setUp() {
        ingredient = new Ingredient(1, "test");
        ingredientDto = new IngredientDto(1, "test");
        ingredientMapper = new IngredientMapperImpl();
    }

    @Test
    void toEntityTest() {
        IngredientDto mappedDto = ingredientMapper.toDto(ingredient);
        assertEquals(ingredient.getId(), mappedDto.id());
        assertEquals(ingredient.getName(), mappedDto.name());
    }

    @Test
    void toDtoTest() {
        Ingredient mappedIngredient = ingredientMapper.toEntity(ingredientDto);
        assertEquals(ingredientDto.id(), mappedIngredient.getId());
        assertEquals(ingredientDto.name(), mappedIngredient.getName());
    }

    @Test
    void toEntityListTest() {
        List<Ingredient> mappedList = ingredientMapper.toEntityList(List.of(ingredientDto));
        assertEquals(1, mappedList.size());
    }

    @Test
    void toDtoListTest() {
        List<IngredientDto> mappedList = ingredientMapper.toDtoList(List.of(ingredient));
        assertEquals(1, mappedList.size());
    }
}
