package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Address;
import org.pizzacrud.dto.AddressDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}
