package com.exalt.estate.service;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.exception.DataAlreadyExistsException;

import com.exalt.estate.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PropertyServiceTest {
    @BeforeEach
    void setUp() throws DataAlreadyExistsException, DataNotFoundException {
        Aerospike.truncateDatabase();
        OwnerService.addOwner("user1", "firstName", "secondName", 1000);
        PropertyService.addProperty("user1", "Armenia", 1000);
    }

    @Test
    void getProperty_GetUser1Data_Pass() throws  DataNotFoundException {
        PropertyDAO property = PropertyService.getProperty(1);
        assertArrayEquals(new String[]{"1", "user1", "Armenia", "1000"},
                new String[]{String.valueOf(property.getPropertyId()), property.getPropertyOwner().getUserName(),
                        property.getAddress(), String.valueOf(property.getCost())});
    }

    @Test
    void addProperty_NonExistingOwner_ExceptionThrown() {
        assertThrowsExactly(DataNotFoundException.class, () -> PropertyService.addProperty("user2", "Armenia", 1000),
                "Add property for non-existing owner");

    }

    @Test
    void deleteProperty_NotExistingProperty_ExceptionThrown() throws DataNotFoundException {
        PropertyService.deleteProperty(1L);
        assertThrowsExactly(DataNotFoundException.class, () -> PropertyService.deleteProperty(1L),
                "Property  not exist");
    }

}