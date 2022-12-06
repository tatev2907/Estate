package com.exalt.estate.mapstruct;


import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.dao.TransactionDAO;
import com.exalt.estate.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO transactionDaoToDto(TransactionDAO transactionDAO);

    ArrayList<TransactionDTO> transactionListDaoToDto(ArrayList<TransactionDAO> transactionDAOS);

    default String map(OwnerDAO value){
        return value.getUserName();
    }

    default int map(PropertyDAO value){
        return value.getPropertyId();
    }
}
