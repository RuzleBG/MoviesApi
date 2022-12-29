package com.soosy.demo.Service;

import java.util.List;
import java.util.Optional;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.MovieNotFoundException;

import jakarta.validation.Valid;

public interface MovieSerivce {
    public List<Movie> getAllMovies();

    public Movie SaveMovie(@Valid Movie movie);

    public Movie findMovieById(long movieId) throws MovieNotFoundException;

    public void deleteMovie(long movieId) throws MovieNotFoundException;
}
