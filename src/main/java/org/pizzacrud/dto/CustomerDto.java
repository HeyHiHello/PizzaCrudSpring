package org.pizzacrud.dto;

public record CustomerDto(int id, String firstname, String lastname, AddressDto address) {
}
