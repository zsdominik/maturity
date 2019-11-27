package com.zsirosd.maturity.controller;

import com.zsirosd.maturity.entity.Actor;
import com.zsirosd.maturity.entity.Booking;
import com.zsirosd.maturity.entity.Movie;
import com.zsirosd.maturity.repository.ActorRepository;
import com.zsirosd.maturity.repository.BookingRepository;
import com.zsirosd.maturity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResourcesController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    BookingRepository bookingRepository;

    @PostMapping("/movies/getAll")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies/create")
    public Movie createNewMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PostMapping("/movies/{id}/bookOne")
    public Booking bookAMovieDummy(@PathVariable Long movieId, @RequestBody Booking bookingDetails) {
        bookingDetails.setMovieId(movieId);
        return bookingRepository.save(bookingDetails);
    }

    @PostMapping("/movies/{id}/update")
    public Movie updateMovie(@PathVariable(value = "id") Long movieId, @RequestBody Movie movieDetails) {
        Movie movie = movieRepository.getOne(movieId);
        movie.setTitle(movieDetails.getTitle());
        movie.setRating(movieDetails.getRating());
        return movieRepository.save(movie);
    }

    @PostMapping("/actors/getAll")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @PostMapping("/actors/create")
    public Actor createNewActor(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }
}
