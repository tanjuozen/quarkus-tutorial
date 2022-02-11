package com.redhat.developers;

import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/fruit")
@RegisterRestClient
public interface FruityViceService {

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(FruityViceFallback.class)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 5000)
    FruityVice getFruitByName(@PathParam("name") String name);

    class FruityViceFallback implements FallbackHandler<FruityVice> {

        private static final FruityVice EMPTY_FRUITY_VICE =
                FruityVice.of("empty", FruityVice.Nutritions.of(0.0, 0.0));

        @Override
        public FruityVice handle(ExecutionContext context) {
            return EMPTY_FRUITY_VICE;
        }
    }
}