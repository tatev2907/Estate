package com.estate.estate.service;

import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Optional;

public class PropertyService {
    private static final PropertyService INSTANCE = new PropertyService();

    private static final Logger logger
            = LoggerFactory.getLogger(PropertyService.class);

    private PropertyService() {
    }

    public static PropertyService getInstance() {
        return INSTANCE;
    }

    public PropertyDAO getProperty(int propertyId) throws DataException {
        Aerospike<PropertyDAO> aerospike = new Aerospike<>(PropertyDAO.class);
        PropertyDAO property = aerospike.getRecord(propertyId);
        if(property == null){
            logger.warn("Property {} not found!", propertyId);
            throw new DataException(String.format("Property %s not found!", propertyId));
        }
        return property;
    }


    public PropertyDAO addProperty(String ownerUserName, String propertyAddress, long cost) throws DataException {
        int propertyId = generateID();
        OwnerDAO owner = OwnerService.getInstance().getOwner(ownerUserName);
        if (owner == null) {
            logger.warn(String.format("Owner of username %s was not found!", ownerUserName));
            throw new DataException(String.format("Owner with username %s does not exists.", ownerUserName));
        }
        PropertyDAO newProperty = new PropertyDAO(propertyId, propertyAddress, owner, cost);
        new Aerospike<>(PropertyDAO.class).saveRecord(newProperty);
        return newProperty;
    }

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
    public PropertyDAO deleteProperty(Long id) throws DataException {
        PropertyDAO record = new Aerospike<>(PropertyDAO.class).getRecord(id);
        if (record == null) {
            logger.warn(String.format("Property with id %s was not found!", id));
            throw new DataException(String.format("Property with id %s was not found!", id));
        }
        new Aerospike<>(PropertyDAO.class).deleteRecord(record);
        return record;

    }
}
