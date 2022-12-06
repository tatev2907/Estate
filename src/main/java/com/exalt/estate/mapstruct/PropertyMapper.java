package com.exalt.estate.mapstruct;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dto.PropertyDTO;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dao.PropertyDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    PropertyDTO propertyDaoToDto(PropertyDAO propertyDAO);

    default String map(OwnerDAO value) {
        return value.getUserName();
    }

    default OwnerDAO map(String userName) {
        Aerospike<OwnerDAO> reader = new Aerospike<>(OwnerDAO.class);
        return reader.getRecord(userName);
    }


}
