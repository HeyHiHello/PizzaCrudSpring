package org.pizzacrud.database.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m2m_Pizzas_Orders",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "pizza_id")})
    private List<Pizza> pizzas;

    public Order() {
    }

    public Order(int id, Customer customer, List<Pizza> pizzas) {
        this.id = id;
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public Order(Customer customer, List<Pizza> pizzas) {
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
