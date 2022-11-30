package com.estate.estate.service;

import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.exception.DataException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PropertyServiceTest {
    @BeforeEach
    void setUp() throws DataException {
        Aerospike.truncateDatabase();
        OwnerService.getInstance().addOwner("user1", "firstName", "secondName", 1000);
        PropertyService.getInstance().addProperty("user1", "Armenia", 1000);
    }

    @Test
    void getProperty() throws DataException {
        PropertyDAO property = PropertyService.getInstance().getProperty(1);
        assertArrayEquals(new String[]{"1", "user1", "Armenia", "1000"},
                new String[]{String.valueOf(property.getPropertyId()), property.getPropertyOwner().getUserName(),
                        property.getAddress(), String.valueOf(property.getCost())});
    }

    @Test
    void addProperty_NonExistingOwner() throws DataException {
        assertThrowsExactly(DataException.class, () -> PropertyService.getInstance().addProperty("user2", "Armenia", 1000),
                "Add property for non-existing owner");

    }

    @Test
    void deleteProperty() throws DataException {
        PropertyService.getInstance().deleteProperty(1L);
        assertThrowsExactly(DataException.class, () -> PropertyService.getInstance().deleteProperty(1L),
                "Property  not exist");
    }

}