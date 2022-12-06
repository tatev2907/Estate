package com.exalt.estate.controller.soap;

import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dao.TransactionDAO;
import com.exalt.estate.dto.TransactionDTO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.exception.RequestFailedException;
import com.exalt.estate.mapstruct.TransactionMapper;
import com.exalt.estate.service.OwnerService;
import com.exalt.estate.service.TransactionService;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService

public class SoapServices {

    @WebMethod(operationName = "add_to_owner_balance")
    public String addBalance(String userName, int balance) {
        OwnerDAO ownerDAO;
        try {
            ownerDAO = OwnerService.addOwnerBalance(
                    OwnerService.getOwner(userName),
                    balance);
        } catch (DataNotFoundException e) {
            return e.getMessage();
        }
        return ownerDAO.toString();
    }

    @WebMethod(operationName = "buy_property")
    public TransactionDTO buyProperty(String buyerID, int propertyID) throws DataNotFoundException, RequestFailedException {
        TransactionDAO transactionDAO = TransactionService.makeTransaction(buyerID, propertyID);
        return TransactionMapper.INSTANCE.transactionDaoToDto(transactionDAO);
    }
}
