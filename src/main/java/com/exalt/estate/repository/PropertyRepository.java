package com.exalt.estate.repository;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Optional;

public class PropertyRepository {

    private static final PropertyRepository INSTANCE = new PropertyRepository();

    private static final Logger logger
            = LoggerFactory.getLogger(PropertyRepository.class);

    private PropertyRepository() {
    }
    /**
     * Get the singleton instance of the property service.
     *
     * @return the property service instance
     */
    public static PropertyRepository getInstance() {
        return INSTANCE;
    }

    public PropertyDAO getProperty(int propertyId) throws DataNotFoundException {
        Aerospike<PropertyDAO> aerospike = new Aerospike<>(PropertyDAO.class);
        PropertyDAO property = aerospike.getRecord(propertyId);
        if (property == null) {
            logger.warn("Property {} not found!", propertyId);
            throw new DataNotFoundException(String.format("Property %s not found!", propertyId));
        }
        return property;
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
    public PropertyDAO addProperty(String ownerUserName, String propertyAddress, long cost) throws DataNotFoundException {
        int propertyId = generateID();
        OwnerDAO owner = OwnerService.getOwner(ownerUserName);
        if (owner == null) {
            logger.warn(String.format("Owner of username %s was not found!", ownerUserName));
            throw new DataNotFoundException(String.format("Owner with username %s does not exists.", ownerUserName));
        }
        PropertyDAO newProperty = new PropertyDAO(propertyId, propertyAddress, owner, cost);
        new Aerospike<>(PropertyDAO.class).saveRecord(newProperty);
        return newProperty;
    }
    /**
     * Generate new unique property id.
     *
     * @return the new id of type int
     */
    private static int generateID() {
        Aerospike<PropertyDAO> aerospike = new Aerospike<>(PropertyDAO.class);
        Optional<PropertyDAO> lastPropertyID = aerospike.getSet()
                .stream()
                .max(Comparator.comparing(PropertyDAO::getPropertyId));
        int maxId = 0;
        if (lastPropertyID.isPresent())
            maxId = lastPropertyID.get().getPropertyId();
        return maxId + 1;
    }

    /**
     * Delete a specific property from the database.
     *
     * @param id the unique property id
     * @return the deleted property
     * @throws DataNotFoundException the data not found exception
     */
    public PropertyDAO deleteProperty(Long id) throws DataNotFoundException {
        PropertyDAO record = new Aerospike<>(PropertyDAO.class).getRecord(id);
        if (record == null) {
            logger.warn(String.format("Property with id %s was not found!", id));
            throw new DataNotFoundException(String.format("Property with id %s was not found!", id));
        }
        new Aerospike<>(PropertyDAO.class).deleteRecord(record);
        return record;

    }
}
