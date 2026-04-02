package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.converter.ModelToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.CatchService;
import fr.epita.assistants.yakamon.domain.service.PlayerService;
import fr.epita.assistants.yakamon.presentation.api.response.CatchResponse;
import fr.epita.assistants.yakamon.presentation.api.response.PlayerResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CatchResource {

    @Inject
    CatchService catchservice;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/catch")
    public Response catchh() {
        CatchResponse DTOs;
        DTOs = ModelToDTOConverter.catchh(catchservice.Catch());

        return Response.ok().entity(DTOs).build();
    }

}
