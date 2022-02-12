package com.redhat.developers;


import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@ApplicationScoped
public class BeerGenerator {

    @RestClient
    BeerService service;

    @Outgoing("beer")
    Multi<String> beers() {
        List<Beer>  beers = service.getBeers(10);
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .map(tick -> beers.get(ThreadLocalRandom.current().nextInt(0, beers.size())))
                .map(JsonbBuilder.create()::toJson);
    }
}