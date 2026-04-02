package fr.epita.assistants.yakamon.presentation.rest;

import fr.epita.assistants.yakamon.converter.EntityToDTOConverter;
import fr.epita.assistants.yakamon.domain.service.InventoryService;
import fr.epita.assistants.yakamon.domain.service.PlayerService;
import fr.epita.assistants.yakamon.presentation.api.response.InventoryResponse;
import fr.epita.assistants.yakamon.presentation.api.response.PlayerResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {

        @Inject
        InventoryService InventoryService;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/inventory")
        public Response inventory() {
            InventoryResponse DTOs;
            DTOs = EntityToDTOConverter.inventory(InventoryService.InventoryService());
            return Response.ok().entity(DTOs).build();
        }


    }
