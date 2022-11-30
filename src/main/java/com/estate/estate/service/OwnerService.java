package com.estate.estate.service;

import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class OwnerService {
    private static final OwnerService INSTANCE = new OwnerService();

    private static final Logger logger
            = LoggerFactory.getLogger(OwnerService.class);


    private OwnerService() {
    }

    public static OwnerService getInstance() {
        return INSTANCE;
    }


    public ArrayList<OwnerDAO> getAllOwners() {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        ArrayList<OwnerDAO> owners = aerospike.getSet();
        if (owners.isEmpty()) {
            logger.warn("No records of owners found on the system");
        }
        return owners;
    }


    public OwnerDAO getOwner(String userName) throws DataException {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        OwnerDAO owner = aerospike.getRecord(userName);
        if (owner == null) {
            logger.warn(String.format("Username %s was not found!", userName));
            throw new DataException(String.format("Username %s does not exists.", userName));
        }
        return owner;
    }


    public OwnerDAO deleteOwner(String userName) throws DataException {
        OwnerDAO owner = getOwner(userName);
        if (owner != null) {
            new Aerospike<>(OwnerDAO.class).deleteRecord(owner);
        }
        return owner;
    }


    public OwnerDAO updateOwner(OwnerDAO ownerUpdated, String userName) throws DataException {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        if (aerospike.getRecord(userName) == null) {
            logger.warn("Owner {} does not exists", userName);
            throw new DataException(String.format("Owner with username %s does not exists.", userName));
        } else {
            aerospike.updateRecord(ownerUpdated, "firstName", "lastName", "balance");
            return aerospike.getRecord(userName);
        }
    }

    public OwnerDAO addOwner(String userName, String firstName, String lastName, long balance) throws DataException {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        OwnerDAO owner = aerospike.getRecord(userName);
        if (owner != null) {
            logger.warn(String.format("Owner with username %s already exists.", userName));
            throw new DataException(String.format("Owner with username %s already exists.", userName));
        }
        OwnerDAO newOwner = new OwnerDAO(userName, firstName, lastName, balance);
        aerospike.saveRecord(newOwner);
        return newOwner;
    }

    public OwnerDAO addOwnerBalance(OwnerDAO ownerDAO, long additionalBalance) {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        if (ownerDAO == null) {
            logger.warn("Owner does not exists");
        }
        ownerDAO.setBalance(ownerDAO.getBalance() + additionalBalance);
        aerospike.updateRecord(ownerDAO, "balance");
        return ownerDAO;
    }

}
