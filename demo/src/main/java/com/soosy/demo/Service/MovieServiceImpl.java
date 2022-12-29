package com.soosy.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Repository.MovieRepository;

import jakarta.validation.Valid;

@Service
public class MovieServiceImpl implements MovieSerivce{

    @Autowired
    MovieRepository movieRepository;
    
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    @Override
    public Movie SaveMovie(@Valid Movie movie) {
        return movieRepository.save(movie);
    }
    @Override
    public Movie findMovieById(long movieId) throws MovieNotFoundException{
        return movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie Not Found"));
    }
    @Override
    public void deleteMovie(long movieId) throws MovieNotFoundException {
        Movie movie=findMovieById(movieId);
        movie.getActors().stream().forEach(x->x.getMovies().clear());
        movie.getActors().clear();
        movieRepository.deleteById(movieId);
        
    }
}
