package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.PizzaDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = IngredientMapper.class)
public interface PizzaMapper {
    PizzaDto toDto(Pizza pizza);
    Pizza toEntity(PizzaDto pizzaDto);
    List<PizzaDto> toDtoList(List<Pizza> ingredientList);
    List<Pizza> toEntityList(List<PizzaDto> ingredientDtoList);
}
