package org.pizzacrud.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "building")
    private String building;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Customer customer;

    public Address() {
    }

    public Address(int id, String city, String street, String building, Customer customer) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.building = building;
        this.customer = customer;
    }

    public Address(String city, String street, String building, Customer customer) {
        this.city = city;
        this.street = street;
        this.building = building;
        this.customer = customer;
    }

    public Address(String city, String street, String building) {
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
