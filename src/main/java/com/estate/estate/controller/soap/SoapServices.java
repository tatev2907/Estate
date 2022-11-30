package com.estate.estate.controller.soap;

import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.DAO.TransactionDAO;
import com.estate.estate.DB.Transaction;
import com.estate.estate.DB.mapstruct.TransactionMap;
import com.estate.estate.exception.DataException;
import com.estate.estate.service.OwnerService;
import com.estate.estate.service.TransactionService;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService

public class SoapServices {

    @WebMethod(operationName = "add_to_owner_balance")
    public String addBalance(String userName, int balance) {
        OwnerDAO ownerDAO;
        try {
            ownerDAO = OwnerService.getInstance().addOwnerBalance(
                    OwnerService.getInstance().getOwner(userName),
                    balance);
        } catch (DataException e) {
            return e.getMessage();
        }
        return ownerDAO.toString();
    }

    @WebMethod(operationName = "buy_property")
    public Transaction buyProperty(String buyerID, int propertyID) {
        TransactionDAO transactionDAO = TransactionService.getInstance().makeTransaction(buyerID, propertyID);
        return TransactionMap.INSTANCE.transactionDaoToDto(transactionDAO);
    }
}
