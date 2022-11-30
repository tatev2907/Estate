package com.estate.estate.service;

import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.DB.DAO.TransactionDAO;
import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TransactionService {

    private static final TransactionService INSTANCE = new TransactionService();

    private static final Logger logger
            = LoggerFactory.getLogger(TransactionService.class);

    private TransactionService() {
    }

    public static TransactionService getInstance() {
        return INSTANCE;
    }

    public ArrayList<TransactionDAO> getTransactions() {
        Aerospike<TransactionDAO> aerospike = new Aerospike<>(TransactionDAO.class);
        ArrayList<TransactionDAO> transactions = aerospike.getSet();
        if (transactions.isEmpty()) {
            logger.info("Transactions doesn't exist");
        }
        return transactions;
    }


    public TransactionDAO makeTransaction(String buyerID, int propertyID) {
        OwnerDAO buyer;
        PropertyDAO property;
        OwnerDAO seller;
        try {
            buyer = OwnerService.getInstance().getOwner(buyerID);
            property = PropertyService.getInstance().getProperty(propertyID);
            seller = OwnerService.getInstance().getOwner(property.getPropertyOwner().getUserName());
        } catch (DataException e) {
            return null;
        }
        if (buyer.getBalance() < property.getCost()) {
            logger.warn("Transaction failed as owner balance not sufficient");
        }
        if (property.isForSale()) {
            logger.warn("Property isn't for sale");
        }
        OwnerService.getInstance().addOwnerBalance(buyer, -property.getCost());
        OwnerService.getInstance().addOwnerBalance(seller, property.getCost());
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
