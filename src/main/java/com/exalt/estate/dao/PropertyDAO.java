package com.exalt.estate.dao;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.aerospike.mapper.annotations.AerospikeReference;
import com.exalt.estate.aerospike.Aerospike;

/**
 * PropertyDAO is the property data access object.
 * The object that's used between datalayer and service layer.
 */
@AerospikeRecord(namespace = Aerospike.namespace, set = Aerospike.propertySet)
public class PropertyDAO {
    @AerospikeKey
    private int propertyId;
    private String address;
    @AerospikeReference
    private OwnerDAO propertyOwner;
    private long cost;
    private boolean forSale;

    /**
     * Instantiates a new Property dao.
     */
    public PropertyDAO() {
    }

    /**
     * Instantiates a new Property dao.
     *
     * @param propertyId    the property id is the unique id for property
     * @param address       the address
     * @param propertyOwner the property owner of type OwnerDAO
     * @param cost          the cost
     */
    public PropertyDAO(int propertyId, String address, OwnerDAO propertyOwner, long cost) {
        this.forSale = true;
        this.propertyId = propertyId;
        this.address = address;
        this.propertyOwner = propertyOwner;
        this.cost = cost;
    }

    /**
     * Gets property id.
     *
     * @return the property id
     */
    public int getPropertyId() {
        return propertyId;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets property owner in type OwnerDAO.
     *
     * @return the property owner
     */
    public OwnerDAO getPropertyOwner() {
        return propertyOwner;
    }

    /**
     * Sets property owner.
     *
     * @param propertyOwner the property owner
     */
    public void setPropertyOwner(OwnerDAO propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public long getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(long cost) {
        this.cost = cost;
    }

    /**
     * Gets for sale.
     *
     * @return the for sale
     */
    public boolean isForSale() {
        return forSale;
    }

    /**
     * Sets for sale.
     *
     * @param forSale the for sale
     */
    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }
}
