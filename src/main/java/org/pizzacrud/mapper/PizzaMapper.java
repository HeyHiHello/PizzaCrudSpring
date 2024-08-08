package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.PizzaDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PizzaMapper {
    PizzaDto toDto(Pizza pizza);
    Pizza toEntity(PizzaDto pizzaDto);
}
