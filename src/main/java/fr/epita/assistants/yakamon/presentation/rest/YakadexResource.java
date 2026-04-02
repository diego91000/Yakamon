package fr.epita.assistants.yakamon.presentation.rest;


import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.converter.ModelToDTOConverter;
import fr.epita.assistants.yakamon.presentation.api.response.MoveResponse;
import fr.epita.assistants.yakamon.presentation.api.response.YakadexResponse;
import fr.epita.assistants.yakamon.presentation.api.response.YakadexWrapResponse;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import jakarta.inject.Inject;

import fr.epita.assistants.yakamon.domain.service.YakadexService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class YakadexResource {

    @Inject
    YakadexService yakadexservice;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/yakadex")
    public Response yakadex( @QueryParam("only_missing") @DefaultValue("false") Boolean content)
    {

        YakadexWrapResponse DTOs;
        DTOs = EntityToDTOConverter.yakadexList(yakadexservice.getYakadex(content));

        return Response.ok().entity(DTOs).build();
    }

    @GET
    @Path("/yakadex/{id}")
    public Response yakadexbyId( @PathParam("id") Integer content) {
        if (content == null || content < 0)
        {
            ErrorCode.MOVE400_ERROR.throwException();
        }
        YakadexResponse DTOs;
        DTOs =EntityToDTOConverter.yakadexx(yakadexservice.getById(content));

        return Response.ok().entity(DTOs).build();
    }
}