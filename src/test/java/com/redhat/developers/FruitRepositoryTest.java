package com.redhat.developers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
class FruitRepositoryTest {

    @Inject
    FruitRepository repository;

    @Test
    void testFindBySeason() {
        final Fruit fruit = new Fruit();
        fruit.name = "dummy";
        fruit.season = "test_season";

        // given
        repository.persist(fruit);

        //when
        final List<Fruit> actualFruits = repository.findBySeason("test_season");

        // then
        assertNotNull(actualFruits);
        assertFalse(actualFruits.isEmpty(), "List must not be empty");
        assertEquals(fruit, actualFruits.get(0), "Found objects must be the same");
    }

    @Test
    void testFindAllFruits() {
        // when
        final List<Fruit> actualFruits = repository.listAll();

        // then
        assertNotNull(actualFruits);
        assertFalse(actualFruits.isEmpty(), "List must not be empty");
    }
}