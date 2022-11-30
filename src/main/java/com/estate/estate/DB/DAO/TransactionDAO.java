package com.estate.estate.DB.DAO;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.aerospike.mapper.annotations.AerospikeReference;
import com.estate.estate.Aerospike.Aerospike;

import java.sql.Timestamp;


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

    public TransactionDAO() {
    }

    public TransactionDAO(OwnerDAO seller, OwnerDAO buyer, PropertyDAO property) {
        this.date = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.seller = seller;
        this.buyer = buyer;
        this.property = property;
    }


    public OwnerDAO getSeller() {
        return seller;
    }

    public void setSeller(OwnerDAO seller) {
        this.seller = seller;
    }

    public OwnerDAO getBuyer() {
        return buyer;
    }

    public void setBuyer(OwnerDAO buyer) {
        this.buyer = buyer;
    }

    public PropertyDAO getProperty() {
        return property;
    }

    public void setProperty(PropertyDAO property) {
        this.property = property;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

}
