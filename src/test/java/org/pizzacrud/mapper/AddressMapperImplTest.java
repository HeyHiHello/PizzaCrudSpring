package org.pizzacrud.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.entity.Address;
import org.pizzacrud.dto.AddressDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperImplTest {
    private static final AddressMapper addressMapper = new AddressMapperImpl();
    private static Address address;
    private static AddressDto addressDto;

    @BeforeAll
    public static void setUp() {
        address = new Address();
        address.setId(1);
        address.setStreet("testStreet");
        address.setCity("testCity");
        address.setBuilding("testBuilding");

        addressDto = new AddressDto("testStreet", "testCity", "testBuilding");
    }

    @Test
    void toDtoTest() {
        AddressDto dto = addressMapper.toDto(address);
        assertEquals(address.getCity(), dto.city());
        assertEquals(address.getBuilding(), dto.building());
        assertEquals(address.getStreet(), dto.street());
    }

    @Test
    void toEntityTest() {
        Address addressMapped = addressMapper.toEntity(addressDto);
        assertEquals(addressDto.city(), addressMapped.getCity());
        assertEquals(addressDto.building(), addressMapped.getBuilding());
        assertEquals(addressDto.street(), addressMapped.getStreet());
    }
}
