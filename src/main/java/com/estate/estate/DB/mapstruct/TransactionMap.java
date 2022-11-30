package com.estate.estate.DB.mapstruct;


import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.DB.DAO.TransactionDAO;
import com.estate.estate.DB.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface TransactionMap {
    TransactionMap INSTANCE = Mappers.getMapper(TransactionMap.class);

    Transaction transactionDaoToDto(TransactionDAO transactionDAO);

    ArrayList<Transaction> transactionListDaoToDto(ArrayList<TransactionDAO> transactionDAOS);

    default String map(OwnerDAO value){
        return value.getUserName();
    }

    default int map(PropertyDAO value){
        return value.getPropertyId();
    }
}
