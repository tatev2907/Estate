package com.exalt.estate.controller.rest;

import com.exalt.estate.aerospike.Aerospike;
import com.exalt.estate.dao.PropertyDAO;
import com.exalt.estate.dto.PropertyDTO;
import com.exalt.estate.exception.DataNotFoundException;
import com.exalt.estate.mapstruct.PropertyMapper;
import com.exalt.estate.exception.ErrorMessage;
import com.exalt.estate.service.PropertyService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/estate")
public class PropertyController {
    @POST
    @Path("/createProperty")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProperty(PropertyDTO property) {
        try {
            PropertyDAO propertyDAO = PropertyService.addProperty(property.getPropertyOwner(), property.getAddress(), property.getCost());

        } catch (DataNotFoundException e) {
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
            propertyDAO = PropertyService.getProperty(id);
        } catch (DataNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.ok(propertyDAO).build();
    }

    @GET
    @Path("/allProperties")
    public ArrayList<PropertyDAO> getAllProperties() {
        ArrayList<PropertyDAO> records = new Aerospike<>(PropertyDAO.class).getSet();
        return records;

    }

    @DELETE
    @Path("/deleteProperty/{id}")
    public Response deleteProperty(@PathParam("id") Long id) {
        PropertyDAO propertyDAO;
        try {
            propertyDAO = PropertyService.deleteProperty(id);
        } catch (DataNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), 404))
                    .build();
        }
        return Response.accepted(PropertyMapper.INSTANCE.propertyDaoToDto(propertyDAO)).build();
    }


}
