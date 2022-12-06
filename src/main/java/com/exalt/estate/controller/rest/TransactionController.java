package com.exalt.estate.controller.rest;

import com.exalt.estate.dao.TransactionDAO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.service.TransactionService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.ArrayList;

@Path("/estate")

public class TransactionController {
    @GET
    @Path("/transactions")
    public ArrayList<TransactionDAO> getAllTransactions() throws DataNotFoundException {
        ArrayList<TransactionDAO> records = TransactionService.getTransactions();
        return records;
    }
}
