package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.dto.CustomerDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = AddressMapper.class)
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
    List<CustomerDto> toDtoList(List<Customer> customerList);
    List<Customer> toEntityList(List<CustomerDto> customerDtoList);
}
