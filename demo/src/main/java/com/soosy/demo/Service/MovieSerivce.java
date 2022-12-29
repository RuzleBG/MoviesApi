package com.soosy.demo.Service;

import java.util.List;
import java.util.Set;

import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.MovieNotFoundException;

import jakarta.validation.Valid;

public interface MovieSerivce {
    public List<Movie> getAllMovies();

    public Movie SaveMovie(@Valid Movie movie);

    public Movie findMovieById(long movieId) throws MovieNotFoundException;

    public void deleteMovie(long movieId) throws MovieNotFoundException;

    public Movie findMovieByTitle(String title) throws MovieNotFoundException;

    public Set<String> getActors(long id) throws MovieNotFoundException;

    public Movie updateMovie(@Valid Movie movie, long id) throws MovieNotFoundException;
}
