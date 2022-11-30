package com.estate.estate.DB.DAO;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.aerospike.mapper.annotations.AerospikeReference;
import com.estate.estate.Aerospike.Aerospike;

@AerospikeRecord(namespace= Aerospike.namespace, set = Aerospike.propertySet)
public class PropertyDAO {
    @AerospikeKey
    private int propertyId;
    private String address;
    @AerospikeReference
    private OwnerDAO propertyOwner;
    private long cost;
    private boolean forSale;

    public PropertyDAO(){}

    public PropertyDAO(int propertyId , String address, OwnerDAO propertyOwner, long cost) {
        this.forSale = true;
        this.propertyId = propertyId;
        this.address = address;
        this.propertyOwner = propertyOwner;
        this.cost = cost;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OwnerDAO getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(OwnerDAO propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }
}
