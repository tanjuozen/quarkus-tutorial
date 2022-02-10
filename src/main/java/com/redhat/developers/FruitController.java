package com.redhat.developers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/spring-fruit")
public class FruitController {

    private final SpringFruitRepository repository;

    public FruitController(SpringFruitRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Fruit> fruits(@RequestParam("season") String season) {
        if (season != null) {
            return repository.findBySeason(season);
        }
        return repository.findAll();
    }
}
