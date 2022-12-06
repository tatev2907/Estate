package com.exalt.estate.dao;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.aerospike.mapper.annotations.AerospikeReference;
import com.exalt.estate.aerospike.Aerospike;

import java.sql.Timestamp;

/**
 * The type TransactionDAO is the transaction data access object.
 * It is the object that's used between datalayer and service layer.
 */

@AerospikeRecord(namespace = Aerospike.namespace, set = Aerospike.trnasactionSet)
public class TransactionDAO {
    @AerospikeKey
    private String date;
    @AerospikeReference
    private OwnerDAO seller;
    @AerospikeReference
    private OwnerDAO buyer;
    @AerospikeReference
    private PropertyDAO property;
    private Long price;

    /**
     * Instantiates a new Transaction dao.
     */
    public TransactionDAO() {
    }

    /**
     * Instantiates a new Transaction dao.
     *
     * @param seller   the seller in type OwnerDAO
     * @param buyer    the buyer in type OwnerDAO
     * @param property the property in type PropertyDAO
     */
    public TransactionDAO(OwnerDAO seller, OwnerDAO buyer, PropertyDAO property) {
        this.date = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.seller = seller;
        this.buyer = buyer;
        this.property = property;
    }

    /**
     * Gets seller in type OwnerDAO.
     *
     * @return the seller
     */

    public OwnerDAO getSeller() {
        return seller;
    }


    /**
     * Gets buyer in type OwnerDAO.
     *
     * @return the buyer
     */

    public OwnerDAO getBuyer() {
        return buyer;
    }

    /**
     * Gets property in type PropertyDAO.
     *
     * @return the property
     */

    public PropertyDAO getProperty() {
        return property;
    }


    /**
     * Gets the price paid from the buyer to the seller in this transaction.
     *
     * @return the price
     */
    public Long getPrice() {
        return price;
    }


    /**
     * Gets date at which this transaction was created.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

}
