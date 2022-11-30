package com.estate.estate.controller.rest;

import com.estate.estate.DB.DAO.TransactionDAO;
import com.estate.estate.service.TransactionService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/estate")

public class TransactionController {
    @GET
    @Path("/transactions")
    public Response getAllTransactions() {
        ArrayList<TransactionDAO> records = TransactionService.getInstance().getTransactions();
        return Response.ok(records).build();
    }
}
