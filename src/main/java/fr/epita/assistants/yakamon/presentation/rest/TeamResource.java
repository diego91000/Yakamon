package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.ModelToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.TeamService;
import fr.epita.assistants.yakamon.presentation.api.response.TeamWrapResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamResource {
    @Inject
    TeamService teamservice;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/team")
    public Response team()
    {
        TeamWrapResponse DTOs;
        DTOs = ModelToDTOConverter.team(teamservice.TeamService());
        return Response.ok().entity(DTOs).build();
    }
}