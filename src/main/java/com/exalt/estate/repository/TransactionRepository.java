package com.exalt.estate.repository;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.dao.TransactionDAO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.exception.RequestFailedException;
import com.exalt.estate.service.OwnerService;
import com.exalt.estate.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TransactionRepository {

    private static final TransactionRepository INSTANCE = new TransactionRepository();

    private static final Logger logger
            = LoggerFactory.getLogger(TransactionRepository.class);

    private TransactionRepository() {
    }

    /**
     * Get the singleton instance of the transaction service.
     *
     * @return the transaction service instance
     */
    public static TransactionRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Get all the transactions in the database.
     *
     * @return the list of transactions
     * @throws DataNotFoundException the data not found exception
     */
    public ArrayList<TransactionDAO> getTransactions() throws DataNotFoundException {
        Aerospike<TransactionDAO> aerospike = new Aerospike<>(TransactionDAO.class);
        ArrayList<TransactionDAO> transactions = aerospike.getSet();
        if (transactions.isEmpty()) {
            logger.info("Transactions doesn't exist");
            throw new DataNotFoundException("There are no transactions.");

        }
        return transactions;
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

    public TransactionDAO makeTransaction(String buyerID, int propertyID) throws DataNotFoundException, RequestFailedException {
        OwnerDAO buyer = OwnerService.getOwner(buyerID);
        PropertyDAO property = PropertyService.getProperty(propertyID);
        OwnerDAO seller = OwnerService.getOwner(property.getPropertyOwner().getUserName());
        if (!property.isForSale()) {
            logger.warn("Transaction failed as property isn't for sale");
            throw new RequestFailedException("Property not for sale.");
        }
        if (buyer.getBalance() < property.getCost()) {
            logger.warn("Transaction failed as owner balance not sufficient");
            throw new RequestFailedException("Insufficient balance.");
        }
        OwnerService.addOwnerBalance(buyer, -property.getCost());
        OwnerService.addOwnerBalance(seller, property.getCost());
        property.setPropertyOwner(buyer);
        property.setForSale(false);
        new Aerospike<>(PropertyDAO.class).updateRecord(property);
        TransactionDAO newTransaction = new TransactionDAO(seller, buyer, property);
        logger.info("Transaction {} created successfully! Buyer {}, Seller {}, property {}, price {} ",
                newTransaction.getDate(), buyerID, seller.getUserName(), propertyID, property.getCost());
        new Aerospike<>(TransactionDAO.class).saveRecord(newTransaction);
        return newTransaction;
    }

}
