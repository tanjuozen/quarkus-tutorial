package com.redhat.developers;

import io.netty.util.internal.ThreadLocalRandom;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.math.BigDecimal;
import java.util.Random;

@ApplicationScoped
public class PriceGenerator {
    private Random random = new Random();

    @Incoming("beer")
    @Outgoing("priced-beer")
    @Blocking
    public String markup(String price) {
        Jsonb jsonb = JsonbBuilder.create();
        Beer beer = jsonb.fromJson(price, Beer.class);
        PricedBeer pricedBeer = beer.withPrice(new BigDecimal(ThreadLocalRandom.current().nextInt(100, 2000)).setScale(2));
        return jsonb.toJson(pricedBeer);
    }

}