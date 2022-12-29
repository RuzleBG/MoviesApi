package com.soosy.demo.Controllers;

import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.MovieNotFoundException;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Service.MovieSerivce;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/movies")
public class MovieController {
    
    @Autowired
    MovieSerivce movieSerivce;

    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieSerivce.getAllMovies(), HttpStatus.OK); 
    }
    @GetMapping("/id")
    public ResponseEntity<Movie> findMovieById(@RequestParam(value = "movie_id") long movieId) throws MovieNotFoundException{
        return new ResponseEntity<Movie>(movieSerivce.findMovieById(movieId), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Movie> addNewMovie(@Valid @RequestBody Movie movie){
        return new ResponseEntity<Movie>(movieSerivce.SaveMovie(movie), HttpStatus.OK);
    }
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteNewMovie(@RequestParam(value = "movie_id") long movieId) throws MovieNotFoundException{
        movieSerivce.deleteMovie(movieId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    @GetMapping("/title")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam(value = "movie_title") String title) throws MovieNotFoundException{
        return new ResponseEntity<Movie>(movieSerivce.findMovieByTitle(title),HttpStatus.OK);
    }
    @GetMapping("/getactors")
    public ResponseEntity<Set<String>> getActorsOfMovie(@RequestParam(value = "movie_id") long id) throws MovieNotFoundException{
        return new ResponseEntity<Set<String>>(movieSerivce.getActors(id), HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@Valid @RequestBody Movie movie, @RequestParam(value = "movie_id") long id) throws MovieNotFoundException{
        return new ResponseEntity<Movie>(movieSerivce.updateMovie(movie,id), HttpStatus.OK);
    }
}
