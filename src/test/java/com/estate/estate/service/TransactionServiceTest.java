package com.estate.estate.service;

import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.DB.DAO.TransactionDAO;
import com.estate.estate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @BeforeEach
    void setUp() throws DataException {
        Aerospike.truncateDatabase();
        OwnerService.getInstance().addOwner("user1", "firstName", "secondName", 1000);
        OwnerService.getInstance().addOwner("user2", "firstName2", "secondName2", 2000);
        PropertyService.getInstance().addProperty("user1", "Armenia", 1000);
        PropertyService.getInstance().addProperty("user2", "Armenia", 1000);
        TransactionService.getInstance().makeTransaction("user1",1);
    }

    @Test
    void getTransactions() {
        ArrayList<TransactionDAO> transactions = TransactionService.getInstance().getTransactions();
        assertEquals(1, transactions.size());
    }

    @Test
    void makeTransaction() {
        TransactionService.getInstance().makeTransaction("user2",2);
        ArrayList<TransactionDAO> transactions = TransactionService.getInstance().getTransactions();
        assertEquals(2, transactions.size());

    }
}