package com.redhat.developers;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/fruit")
public class FruitResource {


    private final FruitRepository repository;

    public FruitResource(FruitRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruit> fruites(@QueryParam("season") String season) {
        if (season != null) {
            return repository.findBySeason(season);
        }
        return repository.listAll();
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
