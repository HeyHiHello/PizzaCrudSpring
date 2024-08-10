package org.pizzacrud.converter.configuration;

import org.pizzacrud.converter.PizzaDtoByIdConverter;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.dto.PizzaDto;
import org.pizzacrud.mapper.PizzaMapper;
import org.pizzacrud.service.PizzaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class PizzaConverterTestConfiguration {

    @Bean
    @Scope("prototype")
    public Ingredient testIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("test");
        return ingredient;
    }

    @Bean
    public Pizza testPizza() {
        Pizza pizza = new Pizza();
        pizza.setId(1);
        pizza.setName("Pizza");

        Ingredient ingredient1 = new Ingredient(1, "test");
        Ingredient ingredient2 = new Ingredient(2, "test");

        pizza.setIngredients(List.of(ingredient1, ingredient2));

        return pizza;
    }

    @Bean
    public PizzaDto testPizzaDto() {
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setId(1);
        pizzaDto.setName("Pizza");

        IngredientDto ingredient1 = new IngredientDto(1, "test");
        IngredientDto ingredient2 = new IngredientDto(2, "test");

        pizzaDto.setIngredients(List.of(ingredient1, ingredient2));
        return pizzaDto;
    }

    @Bean
    public PizzaMapper mockPizzaMapper() {
        PizzaMapper pizzaMapper = mock(PizzaMapper.class);
        when(pizzaMapper.toDto(any())).thenReturn(testPizzaDto());
        return pizzaMapper;
    }

    @Bean
    public PizzaService mockPizzaService() {
        PizzaService pizzaServiceMock = mock(PizzaService.class);
        when(pizzaServiceMock.findById(anyInt())).thenReturn(testPizza());
        return pizzaServiceMock;
    }

    @Bean
    public PizzaDtoByIdConverter pizzaDtoByIdConverter() {
        return new PizzaDtoByIdConverter(mockPizzaService(), mockPizzaMapper());
    }
}
