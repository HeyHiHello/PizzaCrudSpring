package org.pizzacrud.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.pizzacrud.dto.CustomerDto;
import org.pizzacrud.mapper.CustomerMapper;
import org.pizzacrud.service.CustomerService;
import org.springframework.stereotype.Component;

/**
 * Converter applied while parsing Dto that contains only Customer id
 */
@Component
public class CustomerDtoByIdConverter implements Converter<Integer, CustomerDto> {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerDtoByIdConverter(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto convert(Integer integer) {
        return customerMapper.toDto(customerService.findById(integer));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Integer.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(CustomerDto.class);
    }
}
