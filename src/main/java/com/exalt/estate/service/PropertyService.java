package com.exalt.estate.service;

import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.repository.PropertyRepository;

public class PropertyService {

    private PropertyService() {
    }

    /**
     * Gets a specific property from the database.
     *
     * @param propertyId he unique property id
     * @return the retrieved property
     * @throws DataNotFoundException the data not found exception
     */
    public static PropertyDAO getProperty(int propertyId) throws DataNotFoundException {

        return PropertyRepository.getInstance().getProperty(propertyId);
    }
    /**
     * Add a new property to the database.
     *
     * @param ownerUserName   the owner username
     * @param propertyAddress the property address
     * @param cost            the cost
     * @return the new property data
     * @throws DataNotFoundException the data not found exception
     */

    public static PropertyDAO addProperty(String ownerUserName, String propertyAddress, long cost) throws DataNotFoundException {
        return PropertyRepository.getInstance().addProperty(ownerUserName,propertyAddress,cost);
    }

    /**
     * Delete a specific property from the database.
     *
     * @param id the unique property id
     * @return the deleted property
     * @throws DataNotFoundException the data not found exception
     */
    public static PropertyDAO deleteProperty(Long id) throws DataNotFoundException {
        return PropertyRepository.getInstance().deleteProperty(id);

    }
}
