package com.zsirosd.maturity.controller;

import com.zsirosd.maturity.entity.Actor;
import com.zsirosd.maturity.entity.Movie;
import com.zsirosd.maturity.repository.ActorRepository;
import com.zsirosd.maturity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class ResourcesController {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieRepository movieRepository;

    @PostMapping("/actors")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @PostMapping("/createActor")
    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @PostMapping("/movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies/{id}")
    public Movie getOneMovieById(@PathVariable(value = "id") Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(RuntimeException::new);
    }
}
