package org.pizzacrud.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.mapper.IngredientMapper;
import org.pizzacrud.service.IngredientService;
import org.springframework.stereotype.Component;

/**
 * Converter applied while parsing Dto that contains only Ingredient id
 */
@Component
public class IngredientDtoByIdConverter implements Converter<Integer, IngredientDto> {
    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    public IngredientDtoByIdConverter(IngredientService ingredientService, IngredientMapper ingredientMapper) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public IngredientDto convert(Integer ingredientId) {
        return ingredientMapper.toDto(ingredientService.findById(ingredientId));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Integer.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Ingredient.class);
    }
}
