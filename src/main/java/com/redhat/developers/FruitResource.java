package com.redhat.developers;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/fruit")
public class FruitResource {


    private final FruitRepository repository;

    @RestClient
    FruityViceService fruityViceService;

    public FruitResource(FruitRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FruitDTO> fruites(@QueryParam("season") String season) {
        if (season != null) {
            return repository.findBySeason(season).stream()
                    .map(fruit -> FruitDTO.of(fruit, fruityViceService.getFruitByName(fruit.name)))
                    .collect(Collectors.toList());
        }
        return repository.listAll().stream()
                .map(fruit -> FruitDTO.of(fruit, fruityViceService.getFruitByName(fruit.name)))
                .collect(Collectors.toList());
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newFruit(Fruit fruit) {
        repository.persist(fruit);
        return Response.status(Response.Status.CREATED).entity(fruit).build();
    }
}
