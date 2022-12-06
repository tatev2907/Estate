package com.exalt.estate.service;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.TransactionDAO;
import com.exalt.estate.exception.DataAlreadyExistsException;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.exception.RequestFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @BeforeEach
    void setUp() throws DataAlreadyExistsException, DataNotFoundException, RequestFailedException {
        Aerospike.truncateDatabase();
        OwnerService.addOwner("user1", "firstName", "secondName", 1000);
        OwnerService.addOwner("user2", "firstName2", "secondName2", 2000);
        PropertyService.addProperty("user1", "Armenia", 1000);
        PropertyService.addProperty("user2", "Armenia", 1000);
        TransactionService.makeTransaction("user1",1);
    }

    @Test
    void getTransactions_AllTransactionsCount_CountIs1() throws DataNotFoundException {
        ArrayList<TransactionDAO> transactions = TransactionService.getTransactions();
        assertEquals(1, transactions.size());
    }

    @Test
    void makeTransaction_AddTransaction_CountIs2() throws DataNotFoundException, RequestFailedException {
        TransactionService.makeTransaction("user2",2);
        ArrayList<TransactionDAO> transactions = TransactionService.getTransactions();
        assertEquals(2, transactions.size());

    }
}