package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.PizzaDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PizzaMapper.class)
public interface PizzaListMapper {
    List<PizzaDto> toDtoList(List<Pizza> ingredientList);
    List<Pizza> toEntityList(List<PizzaDto> ingredientDtoList);
}
