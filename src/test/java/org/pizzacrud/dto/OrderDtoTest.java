package org.pizzacrud.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDtoTest {

    @Test
    void testOrderDto() {
        AddressDto addressDto = new AddressDto("", "", "");
        CustomerDto customerDto = new CustomerDto(1, "", "", addressDto);
        OrderDto orderDto = new OrderDto(1, customerDto, List.of());

        orderDto.setId(1);
        orderDto.setCustomer(customerDto);
        orderDto.setPizzas(List.of());

        assertEquals(1, orderDto.getId());
        assertEquals(customerDto, orderDto.getCustomer());
        assertEquals(0, orderDto.getPizzas().size());
    }
}
