package com.redhat.developers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringFruitRepository extends JpaRepository<Fruit, Long> {

    List<Fruit> findBySeason(String season);
}
