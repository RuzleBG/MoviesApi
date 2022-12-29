package com.soosy.demo.Controllers;

import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.MovieNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Service.MovieSerivce;

import jakarta.validation.Valid;


@RestController
public class MovieController {
    
    @Autowired
    MovieSerivce movieSerivce;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieSerivce.getAllMovies(), HttpStatus.OK); 
    }
    @GetMapping("/movies/id")
    public ResponseEntity<Movie> findMovieById(@RequestParam(value = "movie_id") long movieId) throws MovieNotFoundException{
        return new ResponseEntity<Movie>(movieSerivce.findMovieById(movieId), HttpStatus.OK);
    }
    @PostMapping("/movies")
    public ResponseEntity<Movie> addNewMovie(@Valid @RequestBody Movie movie){
        return new ResponseEntity<Movie>(movieSerivce.SaveMovie(movie), HttpStatus.OK);
    }
    @DeleteMapping("/movies")
    public ResponseEntity<HttpStatus> deleteNewMovie(@RequestParam(value = "movie_id") long movieId) throws MovieNotFoundException{
        movieSerivce.deleteMovie(movieId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
