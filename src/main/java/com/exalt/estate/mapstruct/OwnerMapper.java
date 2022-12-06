package com.exalt.estate.mapstruct;

import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dto.OwnerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface OwnerMapper {
    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    OwnerDTO ownerDaoToDto(OwnerDAO ownerDAO);

    OwnerDAO ownerDtoToDao(OwnerDTO ownerDTO);

    ArrayList<OwnerDTO> ownerListDaoToDto(ArrayList<OwnerDAO> ownerDAOS);

}
