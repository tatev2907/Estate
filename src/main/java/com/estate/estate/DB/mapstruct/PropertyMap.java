package com.estate.estate.DB.mapstruct;

import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.DB.Property;
import com.estate.estate.Aerospike.Aerospike;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface PropertyMap {
    PropertyMap INSTANCE = Mappers.getMapper(PropertyMap.class);

    Property propertyDaoToDto(PropertyDAO propertyDAO);

    default String map(OwnerDAO value) {
        return value.getUserName();
    }

    default OwnerDAO map(String userName) {
        Aerospike<OwnerDAO> reader = new Aerospike<>(OwnerDAO.class);
        return reader.getRecord(userName);
    }


}
