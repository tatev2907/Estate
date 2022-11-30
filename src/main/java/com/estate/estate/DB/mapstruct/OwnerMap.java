package com.estate.estate.DB.mapstruct;

import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface OwnerMap {
    OwnerMap INSTANCE = Mappers.getMapper(OwnerMap.class);

    Owner ownerDaoToDto(OwnerDAO ownerDAO);

    OwnerDAO ownerDtoToDao(Owner ownerDTO);

    ArrayList<Owner> ownerListDaoToDto(ArrayList<OwnerDAO> ownerDAOS);

}
