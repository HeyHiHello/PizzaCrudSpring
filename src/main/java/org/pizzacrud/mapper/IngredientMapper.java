package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);
    Ingredient toEntity(IngredientDto ingredientDto);
    List<IngredientDto> toDtoList(List<Ingredient> ingredientList);
    List<Ingredient> toEntityList(List<IngredientDto> ingredientDtoList);
}
