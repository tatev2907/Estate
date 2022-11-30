package com.estate.estate.controller.rest;

import com.estate.estate.DB.DAO.PropertyDAO;
import com.estate.estate.DB.Property;
import com.estate.estate.DB.mapstruct.PropertyMap;
import com.estate.estate.Aerospike.Aerospike;
import com.estate.estate.exception.DataException;
import com.estate.estate.exception.ErrorMessage;
import com.estate.estate.service.PropertyService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/estate")
public class PropertyController {
    @POST
    @Path("/createProperty")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProperty(Property property) {
        try {
            PropertyDAO propertyDAO = PropertyService.getInstance().addProperty(property.getPropertyOwner(), property.getAddress(), property.getCost());

        } catch (DataException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.ok("Added successfully").build();
    }

    @GET
    @Path("/properties/{id}")
    public Response getProperty(@PathParam("id") int id) {
        PropertyDAO propertyDAO;
        try {
            propertyDAO = PropertyService.getInstance().getProperty(id);
        } catch (DataException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.ok(propertyDAO).build();
    }

    @GET
    @Path("/allProperties")
    public Response getAllProperties() {
        ArrayList<PropertyDAO> records = new Aerospike<>(PropertyDAO.class).getSet();
        return Response.ok(records).build();

    }

    @DELETE
    @Path("/deleteProperty/{id}")
    public Response deleteOwner(@PathParam("id") Long id) {
        PropertyDAO propertyDAO;
        try {
            propertyDAO = PropertyService.getInstance().deleteProperty(id);
        } catch (DataException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.accepted(PropertyMap.INSTANCE.propertyDaoToDto(propertyDAO)).build();
    }


}
