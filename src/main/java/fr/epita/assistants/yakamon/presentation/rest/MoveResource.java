package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.MoveService;
import fr.epita.assistants.yakamon.domain.service.PlayerService;
import fr.epita.assistants.yakamon.presentation.api.request.MoveRequest;
import fr.epita.assistants.yakamon.presentation.api.response.MoveResponse;
import fr.epita.assistants.yakamon.presentation.api.response.PlayerResponse;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import fr.epita.assistants.yakamon.utils.ErrorInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoveResource {

    @Inject
    MoveService moveservice;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("move")
    public Response move(MoveRequest content) {
        if (content == null || content.direction == null)
        {
            ErrorCode.MOVE400_ERROR.throwException();
        }
        MoveResponse DTOs;
        DTOs = EntityToDTOConverter.move(moveservice.Move(content.direction));

        return Response.ok().entity(DTOs).build();
    }
}
