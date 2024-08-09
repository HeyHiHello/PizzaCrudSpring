package org.pizzacrud.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.pizzacrud.converter.IngredientDtoByIdConverter;

import java.util.ArrayList;
import java.util.List;

public class PizzaDto {
    private int id;
    private String name;
    private double price;
    @JsonIgnore
    private List<IngredientDto> ingredients;

    public PizzaDto() {
    }

    public PizzaDto(int id, String name, double price, List<IngredientDto> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonSetter(value = "ingredientsIds")
    @JsonDeserialize(contentConverter = IngredientDtoByIdConverter.class)
    private void setIngredientsDto(List<IngredientDto> ingredients){
        this.ingredients = ingredients;
    }

    @JsonGetter(value = "ingredientsIds")
    private List<Integer> getIngredientsIds() {
        return ingredients.stream()
                .mapToInt(IngredientDto::id)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
