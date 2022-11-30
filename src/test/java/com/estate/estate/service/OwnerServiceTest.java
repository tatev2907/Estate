package com.estate.estate.service;

import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceTest {

    @BeforeEach
    void setUp() throws DataException {
        Aerospike.truncateDatabase();
        OwnerService.getInstance().addOwner("user1", "firstName", "secondName", 1000);
    }
    @Test
    void getOwner() throws DataException {
        OwnerDAO owner = OwnerService.getInstance().getOwner("user1");
        assertEquals("firstName", owner.getFirstName());
    }

    @Test
    void getAllOwners() {
        ArrayList<OwnerDAO> owners = OwnerService.getInstance().getAllOwners();
        assertEquals(1, owners.size());
    }

    @Test
    void deleteOwner_NonExistingOwner() {
        assertThrowsExactly(DataException.class, () -> OwnerService.getInstance().deleteOwner("user5"),
                "Deleting a non-existing owner");
    }

    @Test
    void deleteOwner() throws DataException {
        OwnerService.getInstance().deleteOwner("user1");
        ArrayList<OwnerDAO> owners = OwnerService.getInstance().getAllOwners();
        assertEquals(0, owners.size());
    }

    @Test
    void updateOwner_NonExistingOwner() {
        OwnerDAO updated = new OwnerDAO("user12", "newFirstName", "newLastName", 2000);
        assertThrows(DataException.class, () -> OwnerService.getInstance().updateOwner(updated, "user12"),
                "Updating non existing owner should throw an exception");
    }

    @Test
    void addOwner() throws DataException {
        OwnerService.getInstance().addOwner("user2", "firstName2", "lastName2", 12000);
        ArrayList<OwnerDAO> owners = OwnerService.getInstance().getAllOwners();
        assertEquals(2, owners.size());
    }


    @Test
    void addOwnerBalance() throws DataException {
        OwnerDAO owner = new OwnerDAO("user12", "newFirstName", "newLastName", 2000);
        OwnerService.getInstance().addOwnerBalance(owner, 1000);
        assertEquals(3000, OwnerService.getInstance().getOwner("user1").getBalance());

    }
}