package com.exalt.estate.service;

import com.exalt.estate.dao.TransactionDAO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.exception.RequestFailedException;
import com.exalt.estate.repository.TransactionRepository;

import java.util.ArrayList;

public class TransactionService {
    /**
     * Get all the transactions in the database.
     *
     * @return the list of transactions
     * @throws DataNotFoundException the data not found exception
     */

    public static ArrayList<TransactionDAO> getTransactions() throws DataNotFoundException {

        return TransactionRepository.getInstance().getTransactions();
    }

    /**
     * Transfers ownership of a property from an owner to another and save the data to a new transaction
     *
     * @param buyerID    the buyer id
     * @param propertyID the property id
     * @return the new generated transaction data
     * @throws DataNotFoundException  the data not found exception
     * @throws RequestFailedException the request failed exception which occurs due to a failure in preconditions
     */
    public static TransactionDAO makeTransaction(String buyerID, int propertyID) throws DataNotFoundException, RequestFailedException {
        return TransactionRepository.getInstance().makeTransaction(buyerID, propertyID);
    }

}
