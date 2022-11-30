package com.estate.estate.DB.DAO;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.estate.estate.Aerospike.Aerospike;

@AerospikeRecord(namespace = Aerospike.namespace, set= Aerospike.ownersSet)
public class OwnerDAO {
    @AerospikeKey
    private String userName;
    private String firstName;

    private String lastName;
    private long balance;

    public OwnerDAO() {
    }

    public OwnerDAO(String userName, String firstName, String lastName, long balance) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}