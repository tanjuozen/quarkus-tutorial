package com.redhat.developers;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FruitRepository implements PanacheRepository<Fruit> {
    public List<Fruit> findBySeason(String season) {
        return find("season", season).list();
    }
}

