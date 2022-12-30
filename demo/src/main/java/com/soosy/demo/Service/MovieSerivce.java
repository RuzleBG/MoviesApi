package com.soosy.demo.Service;

import org.springframework.data.domain.Page;

import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.FieldNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;

import jakarta.validation.Valid;

public interface MovieSerivce {
    public Page<Movie> getAllMovies(int page, int size, String field) throws FieldNotFoundException;

    public Movie SaveMovie(@Valid Movie movie);

    public Movie findMovieById(long movieId) throws MovieNotFoundException;

    public void deleteMovie(long movieId) throws MovieNotFoundException;

    public Page<Movie> findMovieByTitle(int page, int size, String title) throws MovieNotFoundException;

    public Page<String> getActors(int page, int size, long id) throws MovieNotFoundException;

    public Movie updateMovie(@Valid Movie movie, long id) throws MovieNotFoundException;
}
