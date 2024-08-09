package org.pizzacrud.database.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    Address address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    List<Order> orders;

    public Customer() {
    }

    public Customer(int id, String firstname, String lastname, Address address, List<Order> orders) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.orders = orders;
    }

    public Customer(String firstname, String lastname, Address address, List<Order> orders) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.orders = orders;
    }

    public Customer(String firstname, String lastname, Address address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
