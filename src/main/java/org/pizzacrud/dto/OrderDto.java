package org.pizzacrud.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.pizzacrud.converter.CustomerDtoByIdConverter;
import org.pizzacrud.converter.PizzaDtoByIdConverter;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private int id;
    @JsonIgnore
    private CustomerDto customer;

    @JsonIgnore
    private List<PizzaDto> pizzas;

    public OrderDto() {
    }

    public OrderDto(int id, CustomerDto customer, List<PizzaDto> pizzas) {
        this.id = id;
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<PizzaDto> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaDto> pizzas) {
        this.pizzas = pizzas;
    }

    @JsonDeserialize(converter = CustomerDtoByIdConverter.class)
    @JsonSetter("customerId")
    private void setCustomerDeserialize(CustomerDto customer) {
        this.customer = customer;
    }

    @JsonDeserialize(contentConverter = PizzaDtoByIdConverter.class)
    @JsonSetter("pizzaIds")
    private void setPizzaDeserialize(List<PizzaDto> pizzas) {
        this.pizzas = pizzas;
    }

    @JsonGetter
    private int getCustomerId() {
        return customer.id();
    }

    @JsonGetter
    private List<Integer> getPizzaIds() {
        return pizzas.stream()
                .mapToInt(PizzaDto::getId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
