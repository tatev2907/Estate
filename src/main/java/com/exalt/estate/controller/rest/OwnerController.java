package com.exalt.estate.controller.rest;
import com.exalt.estate.dao.OwnerDAO;
import com.exalt.estate.dto.OwnerDTO;
import com.exalt.estate.exception.DataAlreadyExistsException;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.mapstruct.OwnerMapper;
import com.exalt.estate.exception.ErrorMessage;
import com.exalt.estate.service.OwnerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/estate")
public class OwnerController {
    @GET
    @Path("/getOwners")
    public ArrayList<OwnerDTO> getOwners(){
        ArrayList<OwnerDTO> owners;
        owners = OwnerMapper.INSTANCE.ownerListDaoToDto(OwnerService.getAllOwners());
        return owners;
    }

    @POST
    @Path("/createOwner")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOwner(OwnerDTO owner) {
        try {
            OwnerDAO ownerDAO = OwnerService.addOwner(owner.getUserName(), owner.getFirstName(), owner.getLastName(), owner.getBalance());
        } catch (DataAlreadyExistsException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorMessage(e.getMessage(), 409))
                    .build();
        }
        return Response.ok(owner.getUserName() + "added").build();
    }

    @PUT
    @Path("/updateOwner/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOwner(@PathParam("userName") String userName, OwnerDTO owner) {
        owner.setUserName(userName);
        OwnerDAO ownerUpdatedDAO = OwnerMapper.INSTANCE.ownerDtoToDao(owner);
        try {
            OwnerService.updateOwner(ownerUpdatedDAO, userName);
        } catch (DataNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        OwnerDTO ownerUpdatedDTO = OwnerMapper.INSTANCE.ownerDaoToDto(ownerUpdatedDAO);
        return Response.accepted(ownerUpdatedDTO).build();
    }

    @DELETE
    @Path("/deleteOwner/{username}")
    public Response deleteOwner(@PathParam("username") String userName) {
        OwnerDAO owner;
        try {
            owner = OwnerService.deleteOwner(userName);
        } catch (DataNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.accepted(OwnerMapper.INSTANCE.ownerDaoToDto(owner)).build();

    }


}