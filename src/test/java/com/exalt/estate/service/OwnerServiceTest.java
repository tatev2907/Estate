package com.exalt.estate.service;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.exception.DataAlreadyExistsException;
import com.exalt.estate.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceTest {

    @BeforeEach
    void setUp() throws DataAlreadyExistsException {
        Aerospike.truncateDatabase();
        OwnerService.addOwner("user1", "firstName", "secondName", 1000);
    }
    @Test
    void getOwner_GetUser1FistName_FirstName() throws  DataNotFoundException {
        OwnerDAO owner = OwnerService.getOwner("user1");
        assertEquals("firstName", owner.getFirstName());
    }

    @Test
    void getAllOwners_OwnersCount_One()  {
        ArrayList<OwnerDAO> owners = OwnerService.getAllOwners();
        assertEquals(1, owners.size());
    }

    @Test
    void deleteOwner_NonExistingOwner_ExceptionThrown() {
        assertThrowsExactly(DataNotFoundException.class, () -> OwnerService.deleteOwner("user5"),
                "Deleting a non-existing owner");
    }

    @Test
    void deleteOwner_GetUserAfterDelete_ExceptionThrown() throws  DataNotFoundException {
        OwnerService.deleteOwner("user1");
        assertThrows(DataNotFoundException.class, () -> OwnerService.getAllOwners(),
                "There is no records for any owner in the database throw an exception");
    }

    @Test
    void updateOwner_NonExistingOwner_ExceptionThrown() {
        OwnerDAO updated = new OwnerDAO("user12", "newFirstName", "newLastName", 2000);
        assertThrows(DataNotFoundException.class, () -> OwnerService.updateOwner(updated, "user12"),
                "Updating non existing owner should throw an exception");
    }

    @Test
    void addOwner_NewUser_UserCountIsTwo() throws DataAlreadyExistsException {
        OwnerService.addOwner("user2", "firstName2", "lastName2", 12000);
        ArrayList<OwnerDAO> owners = OwnerService.getAllOwners();
        assertEquals(2, owners.size());
    }


    @Test
    void addOwnerBalance_AddBalance1000ForNowExistingUser_ExceptionThrown() {
        OwnerDAO owner = new OwnerDAO("user12", "newFirstName", "newLastName", 2000);
        assertThrows(DataNotFoundException.class, () -> OwnerService.addOwnerBalance(owner, 1000),
                "Add balance non existing owner should throw an exception");

    }
}