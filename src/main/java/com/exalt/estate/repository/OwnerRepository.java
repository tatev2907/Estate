package com.exalt.estate.repository;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.exception.DataAlreadyExistsException;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.service.OwnerService;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwnerRepository{    private static final OwnerRepository INSTANCE = new OwnerRepository();

    private static final Logger logger
            = LoggerFactory.getLogger(OwnerService.class);


    private OwnerRepository() {
    }
    /**
     * Get the singleton instance of the owner service.
     *
     * @return the owner service instance
     */
    public static OwnerRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Gets all owners inside the database.
     *
     * @return the list of all owners
     */
    public ArrayList<OwnerDAO> getAllOwners() {
        logger.info("Getting all owners records from database");
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        ArrayList<OwnerDAO> owners = aerospike.getSet();
        return owners;
    }

    /**
     * Gets a specific owner from the database.
     *
     * @param userName the unique username of the owner
     * @return the owner
     * @throws DataNotFoundException the data not found exception
     */
    public OwnerDAO getOwner(String userName) throws DataNotFoundException{
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        OwnerDAO owner = aerospike.getRecord(userName);
        if (owner == null) {
            logger.warn(String.format("Username %s was not found!", userName));
            throw new DataNotFoundException(String.format("Username %s does not exists.", userName));
        }
        return owner;
    }
    /**
     * Delete a specific owner record from database.
     *
     * @param userName the unique username of the owner
     * @return the deleted owner
     * @throws DataNotFoundException  the data not found exception
     */

    public OwnerDAO deleteOwner(String userName) throws DataNotFoundException {
        OwnerDAO owner = getOwner(userName);
        if (owner != null) {
            new Aerospike<>(OwnerDAO.class).deleteRecord(owner);
        }
        return owner;
    }

    /**
     * Update a specific owner record.
     *
     * @param ownerUpdated the updated owner data sent from the controller layer
     * @param userName     the unique owner username
     * @return the updated owner data retrieved from the database
     * @throws DataNotFoundException the data not found exception
     */
    public OwnerDAO updateOwner(OwnerDAO ownerUpdated, String userName) throws DataNotFoundException {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        if (aerospike.getRecord(userName) == null) {
            logger.warn("Owner {} does not exists", userName);
            throw new DataNotFoundException(String.format("Owner with username %s does not exists.", userName));
        } else {
            aerospike.updateRecord(ownerUpdated, "firstName", "lastName", "balance");
            return aerospike.getRecord(userName);
        }
    }

    /**
     * Add a new owner record to the database.
     *
     * @param userName  the new owner's username
     * @param firstName the first name
     * @param lastName  the last name
     * @param balance   the balance
     * @return the newly added owner data
     * @throws DataAlreadyExistsException the data already exists exception
     */
    public OwnerDAO addOwner(String userName, String firstName, String lastName, long balance) throws DataAlreadyExistsException {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        OwnerDAO owner = aerospike.getRecord(userName);
        if (owner != null) {
            logger.warn(String.format("Owner with username %s already exists.", userName));
            throw new DataAlreadyExistsException(String.format("Owner with username %s already exists.", userName));
        }
        logger.info("Adding new owner {}", userName);
        OwnerDAO newOwner = new OwnerDAO(userName, firstName, lastName, balance);
        aerospike.saveRecord(newOwner);
        return newOwner;
    }
    /**
     * Adds balance to a specific owner
     *
     * @param ownerDAO          the owner object
     * @param additionalBalance the additional balance to ada
     * @return the updated owner data
     * @throws DataNotFoundException the data not found exception
     */
    public OwnerDAO addOwnerBalance(OwnerDAO ownerDAO, long additionalBalance) throws DataNotFoundException {
        Aerospike<OwnerDAO> aerospike = new Aerospike<>(OwnerDAO.class);
        if (getOwner(ownerDAO.getUserName()) == null) {
            logger.warn("Owner does not exists");
            throw new DataNotFoundException("Owner doesn't exist");

        }
        ownerDAO.setBalance(ownerDAO.getBalance() + additionalBalance);
        aerospike.updateRecord(ownerDAO, "balance");
        return ownerDAO;
    }
}
