package com.zsirosd.maturity.controller;

import com.zsirosd.maturity.entity.Movie;
import com.zsirosd.maturity.repository.ActorRepository;
import com.zsirosd.maturity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class SwampOfPOXController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @PostMapping("/movieService")
    public Object movieService(@RequestBody LinkedHashMap body) {
        String action = String.valueOf(body.get("action"));
        if (null != action) {
            switch (action) {
                case "getAllMovies": return movieRepository.findAll();
                case "createMovie": {
                    String title = String.valueOf(body.get("title"));
                    Integer rating = (Integer) body.get("rating");

                    Movie movie = new Movie();
                    movie.setRating(rating);
                    movie.setTitle(title);

                    return movieRepository.save(movie);
                }
                case "getAllActors": return actorRepository.findAll();
            }
        }
        return body;
    }
}
