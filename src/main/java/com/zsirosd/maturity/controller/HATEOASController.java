package com.zsirosd.maturity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsirosd.maturity.entity.Actor;
import com.zsirosd.maturity.entity.Booking;
import com.zsirosd.maturity.entity.Movie;
import com.zsirosd.maturity.repository.ActorRepository;
import com.zsirosd.maturity.repository.BookingRepository;
import com.zsirosd.maturity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HATEOASController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ObjectMapper mapper;

    @GetMapping("hateoas/movies")
    public List<HateoasMovie> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<HateoasMovie> hateoasMovies = new ArrayList<>();
        movies.forEach(movie -> {
            HateoasMovie hateoasMovie = new HateoasMovie();
            List<Map<String, String>> links = new ArrayList<>();
            hateoasMovie.setMovie(movie);
            hateoasMovie.setLinks(links);
            // book
            Map<String, String> bookingLink = new HashMap<>();
            bookingLink.put("rel", "/linkrels/movies/bookAMovie");
            bookingLink.put("uri", "/hateoas/movies/" + movie.getMovieId() + "/book");

            //getOne
            Map<String, String> getOneMovieLink = new HashMap<>();
            getOneMovieLink.put("rel", "/linkrels/movies/getOneMovie");
            getOneMovieLink.put("uri", "/hateoas/movies/" + movie.getMovieId());

            links.add(bookingLink);
            links.add(getOneMovieLink);
            hateoasMovies.add(hateoasMovie);
        });
        return hateoasMovies;
    }

    @PostMapping("hateoas/movies")
    public ResponseEntity<Movie> createNewMovie(@RequestBody Movie movie) throws URISyntaxException {
        Movie storedMovie = movieRepository.save(movie);
        return ResponseEntity.created(new URI("/movies/" + storedMovie.getMovieId())).body(storedMovie);
    }

    @PostMapping("hateoas/movies/{id}/book")
    public ResponseEntity<Booking> bookAMovieDummy(@PathVariable("id") Long movieId, @RequestBody Booking bookingDetails) throws URISyntaxException {
        bookingDetails.setMovieId(movieId);
        Booking storedBooking = bookingRepository.save(bookingDetails);
        return ResponseEntity.created(new URI("/bookings/" + storedBooking.getBookingId())).body(storedBooking);
    }

    @PutMapping("hateoas/movies/{id}")
    public Movie updateMovie(@PathVariable("id") Long movieId, @RequestBody Movie movieDetails) {
        Movie movie = movieRepository.getOne(movieId);
        movie.setTitle(movieDetails.getTitle());
        movie.setRating(movieDetails.getRating());
        return movieRepository.save(movie);
    }

    @GetMapping("hateoas/actors")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @PostMapping("hateoas/actors")
    public ResponseEntity<Actor> createNewActor(@RequestBody Actor actor) throws URISyntaxException {
        Actor storedActor = actorRepository.save(actor);
        return ResponseEntity.created(new URI("/actors/" + storedActor.getActorId())).body(storedActor);
    }

    private class HateoasMovie {
        Movie movie;
        List<Map<String, String>> links;

        public HateoasMovie() {
        }

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public List<Map<String, String>> getLinks() {
            return links;
        }

        public void setLinks(List<Map<String, String>> links) {
            this.links = links;
        }
    }
}
