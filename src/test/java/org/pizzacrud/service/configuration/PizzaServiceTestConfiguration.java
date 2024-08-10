package org.pizzacrud.service.configuration;

import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.database.repository.IngredientRepository;
import org.pizzacrud.database.repository.PizzaRepository;
import org.pizzacrud.service.PizzaService;
import org.pizzacrud.service.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@Import(IngredientServiceTestConfiguration.class)
public class PizzaServiceTestConfiguration {

    @Autowired
    IngredientServiceTestConfiguration ingredientConfiguration;

    @Bean
    @Scope("prototype")
    public Pizza testPizza() {
        Pizza pizza = new Pizza();
        pizza.setName("testPizza");
        pizza.setIngredients(List.of(ingredientConfiguration.testIngredient(), ingredientConfiguration.testIngredient()));
        return pizza;
    }

    @Bean
    public PizzaRepository mockPizzaRepository() {
        PizzaRepository pizzaRepository = mock(PizzaRepository.class);
        when(pizzaRepository.findAll()).thenReturn(List.of(testPizza(), testPizza()));
        when(pizzaRepository.findById(anyInt())).thenReturn(Optional.empty());
        Pizza pizzaWithId = testPizza();
        pizzaWithId.setId(1);
        when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizzaWithId));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizzaWithId);
        when(pizzaRepository.existsById(anyInt())).thenReturn(false);
        when(pizzaRepository.existsById(1)).thenReturn(true);
        return pizzaRepository;
    }

    @Bean
    public PizzaService pizzaService() {
        return new PizzaServiceImpl(mockPizzaRepository(), ingredientConfiguration.mockIngredientRepository());
    }
}
