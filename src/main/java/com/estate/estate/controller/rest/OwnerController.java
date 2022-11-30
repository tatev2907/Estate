package com.estate.estate.controller.rest;
import com.estate.estate.DB.DAO.OwnerDAO;
import com.estate.estate.DB.Owner;
import com.estate.estate.DB.mapstruct.OwnerMap;
import com.estate.estate.exception.DataException;
import com.estate.estate.exception.ErrorMessage;
import com.estate.estate.service.OwnerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/estate")
public class OwnerController {
    @GET
    @Path("/getOwners")
    public Response getOwners() {
        ArrayList<Owner> owners;
        owners = OwnerMap.INSTANCE.ownerListDaoToDto(OwnerService.getInstance().getAllOwners());
        return Response.ok(owners).build();
    }

    @POST
    @Path("/createOwner")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOwner(Owner owner) {
        try {
            OwnerDAO ownerDAO = OwnerService.getInstance().addOwner(owner.getUserName(), owner.getFirstName(), owner.getLastName(), owner.getBalance());
        } catch (DataException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.ok(owner.getUserName() + "added").build();
    }

    @PUT
    @Path("/updateOwner/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOwner(@PathParam("userName") String userName, Owner owner) {
        owner.setUserName(userName);
        OwnerDAO ownerUpdatedDAO = OwnerMap.INSTANCE.ownerDtoToDao(owner);
        try {
            OwnerService.getInstance().updateOwner(ownerUpdatedDAO, userName);
        } catch (DataException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        Owner ownerUpdatedDTO = OwnerMap.INSTANCE.ownerDaoToDto(ownerUpdatedDAO);
        return Response.accepted(ownerUpdatedDTO).build();
    }

    @DELETE
    @Path("/deleteOwner/{username}")
    public Response deleteOwner(@PathParam("username") String userName) {

        OwnerDAO owner;
        try {
            owner = OwnerService.getInstance().deleteOwner(userName);
        } catch (DataException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();        }
        return Response.accepted(OwnerMap.INSTANCE.ownerDaoToDto(owner)).build();

    }


}