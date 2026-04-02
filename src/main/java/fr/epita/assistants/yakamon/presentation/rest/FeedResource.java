package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.converter.ModelToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.FeedService;
import fr.epita.assistants.yakamon.domain.service.YakadexService;
import fr.epita.assistants.yakamon.presentation.api.request.FeedRequest;
import fr.epita.assistants.yakamon.presentation.api.response.FeedResponse;
import fr.epita.assistants.yakamon.presentation.api.response.YakadexResponse;
import fr.epita.assistants.yakamon.presentation.api.response.YakadexWrapResponse;
import fr.epita.assistants.yakamon.utils.ErrorCode;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedResource {



    @Inject
    FeedService feedservice;


    @POST
    @Path("/team/{uuid}/feed")
    public Response feed(@PathParam("uuid") UUID content, FeedRequest request) {
        if (request == null || content == null || request.quantity == null || request.quantity < 0)
        {
            System.out.println("des le debut");
            ErrorCode.MOVE400_ERROR.throwException();
        }
        FeedResponse DTOs;
        DTOs = ModelToDTOConverter.feed(feedservice.Feed(content, request.quantity));
        return Response.ok().entity(DTOs).build();
    }
}