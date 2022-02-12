package com.redhat.developers;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.SseElementType;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/beer")
public class BeerResource {

    @RestClient
    BeerService beerService;

    @Channel("priced-beer")
    Multi<String> pricedBeers;


    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Beer> beers() {
        return Multi.createBy().repeating()
                .supplier(
                        () -> new AtomicInteger(1),
                        i -> beerService.getBeers(i.getAndIncrement())
                )
                .until(List::isEmpty)
                .onItem().<Beer>disjoint()
                .select().where(b -> b.getAbv() > 15.0);
    }


    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<PricedBeer> pricedBeers() {
        return pricedBeers.map(s -> JsonbBuilder.create().fromJson(s, PricedBeer.class))
                .ifNoItem().after(Duration.ofSeconds(1)).fail();
    }

}