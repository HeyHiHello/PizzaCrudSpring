package org.pizzacrud.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.pizzacrud.database.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PizzaDto {
    private int id;
    private String name;
    private double price;
    @JsonIgnore
    private List<Ingredient> ingredients;

    public PizzaDto() {
    }

    public PizzaDto(int id, String name, double price, List<Ingredient> ingredients) {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonGetter(value = "ingredientsIds")
    private List<Integer> getIngredientsIds() {
        return ingredients.stream()
                .mapToInt(Ingredient::getId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
