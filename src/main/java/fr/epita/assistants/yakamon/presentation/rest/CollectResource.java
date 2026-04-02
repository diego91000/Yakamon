package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.ModelToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.CatchService;
import fr.epita.assistants.yakamon.domain.service.CollectService;
import fr.epita.assistants.yakamon.presentation.api.response.CatchResponse;
import fr.epita.assistants.yakamon.presentation.api.response.CollectResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CollectResource {

    @Inject
    CollectService collectservice;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/collect")
    public Response collect() {
        CollectResponse DTOs;
        DTOs = ModelToDTOConverter.collect(collectservice.Collect());

        return Response.ok().entity(DTOs).build();
    }
}

