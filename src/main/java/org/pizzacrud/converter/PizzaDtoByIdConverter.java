package org.pizzacrud.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.pizzacrud.dto.PizzaDto;
import org.pizzacrud.mapper.PizzaMapper;
import org.pizzacrud.service.PizzaService;
import org.springframework.stereotype.Component;

/**
 * Converter applied while parsing Dto that contains only Pizza id
 */
@Component
public class PizzaDtoByIdConverter implements Converter<Integer, PizzaDto> {
    private final PizzaService pizzaService;
    private final PizzaMapper pizzaMapper;

    public PizzaDtoByIdConverter(PizzaService pizzaService, PizzaMapper pizzaMapper) {
        this.pizzaService = pizzaService;
        this.pizzaMapper = pizzaMapper;
    }

    @Override
    public PizzaDto convert(Integer integer) {
        return pizzaMapper.toDto(pizzaService.findById(integer));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Integer.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(PizzaDto.class);
    }
}
