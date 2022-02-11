package com.redhat.developers;

import io.smallrye.health.checks.UrlHealthCheck;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class CustomHealthCheck {

    @ConfigProperty(name = "com.redhat.developers.FruityViceService/mp-rest/url")
    String externalURL;

    @Readiness
    HealthCheck checkURL() {
        return new UrlHealthCheck(externalURL + "/api/fruit/banana")
                .name("External URL health check").requestMethod(HttpMethod.GET).statusCode(200);
    }
}
