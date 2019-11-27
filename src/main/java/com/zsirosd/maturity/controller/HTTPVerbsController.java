package com.zsirosd.maturity.controller;

import com.zsirosd.maturity.entity.Actor;
import com.zsirosd.maturity.entity.Movie;
import com.zsirosd.maturity.repository.ActorRepository;
import com.zsirosd.maturity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class HTTPVerbsController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> createNewMovie(@RequestBody Movie movie) throws URISyntaxException {
        Movie storedMovie = movieRepository.save(movie);
        return ResponseEntity.created(new URI("/movies/" + storedMovie.getMovieId())).body(storedMovie);
    }

    @PutMapping("/movies/{id}")
    public Movie updateMovie(@PathVariable(value = "id") Long movieId, @RequestBody Movie movieDetails) {
        Movie movie = movieRepository.getOne(movieId);
        movie.setTitle(movieDetails.getTitle());
        movie.setRating(movieDetails.getRating());
        return movieRepository.save(movie);
    }

    @GetMapping("/actors")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @PostMapping("/actors")
    public ResponseEntity<Actor> createNewActor(@RequestBody Actor actor) throws URISyntaxException {
        Actor storedActor = actorRepository.save(actor);
        return ResponseEntity.created(new URI("/actors/" + storedActor.getActorId())).body(storedActor);
    }
}
