package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.GameService;
import fr.epita.assistants.yakamon.domain.service.PlayerService;
import fr.epita.assistants.yakamon.presentation.api.request.StartRequest;
import fr.epita.assistants.yakamon.presentation.api.response.PlayerResponse;
import fr.epita.assistants.yakamon.presentation.api.response.StartResponse;
import fr.epita.assistants.yakamon.utils.ErrorInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {
    @Inject
    PlayerService playerervice;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/player")
    public Response player() {
        PlayerResponse DTOs;
        DTOs = EntityToDTOConverter.player(playerervice.Player());

        return Response.ok().entity(DTOs).build();
    }


}