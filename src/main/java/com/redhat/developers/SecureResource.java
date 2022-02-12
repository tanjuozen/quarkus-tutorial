package com.redhat.developers;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/secure")
public class SecureResource {

    @Claim(standard = Claims.preferred_username)
    String username;

    @RolesAllowed("Subscriber")
    @GET
    @Path("/claim")
    public String getClaim() {
        return username;
    }
}
