package com.redhat.developers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FruitResourceTest {

    @Inject
    FruitRepository repository;

    @Test
    void testCreatingNewFruit() {
        final Fruit fruit = new Fruit();
        fruit.name = "dummy";
        fruit.season = "test_season";

        given()
                .contentType("application/json")
                .body(fruit)
                .when()
                .post("/fruit")
                .then()
                .statusCode(201);
    }
}