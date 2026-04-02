package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.GameService;
import fr.epita.assistants.yakamon.presentation.api.request.StartRequest;
import fr.epita.assistants.yakamon.presentation.api.response.StartResponse;
import fr.epita.assistants.yakamon.utils.ErrorInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

    @Inject
    GameService gameservice;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/start")
    public Response start(StartRequest content) {
        if (content == null || content.mapPath == null
                ||content.playerName == null
                || content.playerName.isEmpty()
                || content.mapPath.isEmpty())
        {
            ErrorInfo error = new ErrorInfo("Invalid path or invalid name provided.");
            return Response.status(400).entity(error).build();
        }

        StartResponse DTOs;
        DTOs = EntityToDTOConverter.convertor(gameservice.StartService(content.mapPath, content.playerName));

        return Response.ok().entity(DTOs).build();
    }
}