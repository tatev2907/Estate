package com.exalt.estate.service;

import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.exception.DataAlreadyExistsException;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.repository.OwnerRepository;

import java.util.ArrayList;

public class OwnerService {

    /**
     * Gets a specific owner from the database.
     *
     * @param userName the unique username of the owner
     * @return the owner
     * @throws DataNotFoundException the data not found exception
     */
    public static OwnerDAO getOwner(String userName) throws DataNotFoundException {
        return OwnerRepository.getInstance().getOwner(userName);
    }
    /**
     * Gets all owners inside the database.
     *
     * @return the list of all owners
     */
    public static ArrayList<OwnerDAO> getAllOwners() {
        return OwnerRepository.getInstance().getAllOwners();
    }


    /**
     * Delete a specific owner record from database.
     *
     * @param userName the unique username of the owner
     * @return the deleted owner
     * @throws DataNotFoundException  the data not found exception
     */
    public static OwnerDAO deleteOwner(String userName) throws DataNotFoundException {
        return OwnerRepository.getInstance().deleteOwner(userName);
    }

    /**
     * Update a specific owner record.
     *
     * @param ownerUpdated the updated owner data sent from the controller layer
     * @param userName     the unique owner username
     * @return the updated owner data retrieved from the database
     * @throws DataNotFoundException the data not found exception
     */
    public static OwnerDAO updateOwner(OwnerDAO ownerUpdated, String userName) throws DataNotFoundException {
        return OwnerRepository.getInstance().updateOwner(ownerUpdated, userName);

    }

    /**
     * Adds balance to a specific owner
     *
     * @param ownerDAO          the owner object
     * @param additionalBalance the additional balance to ada
     * @return the updated owner data
     * @throws DataNotFoundException the data not found exception
     */
    public static OwnerDAO addOwnerBalance(OwnerDAO ownerDAO, long additionalBalance) throws DataNotFoundException{
        return OwnerRepository.getInstance().addOwnerBalance(ownerDAO,additionalBalance);

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
    public static OwnerDAO addOwner(String userName, String firstName, String lastName, long balance) throws DataAlreadyExistsException {
        return OwnerRepository.getInstance().addOwner(userName, firstName, lastName, balance);

    }


}
