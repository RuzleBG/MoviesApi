package com.soosy.demo.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.FieldNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Repository.MovieRepository;

import jakarta.validation.Valid;

@Service
public class MovieServiceImpl implements MovieSerivce{

    @Autowired
    MovieRepository movieRepository;
    
    @Override
    public Page<Movie> getAllMovies(int page, int size, String field) throws FieldNotFoundException {
        try {
            return movieRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, field));
        } catch (Exception e) {
            throw new FieldNotFoundException("Invalid Field");
        }
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
    @Override
    public Page<Movie> findMovieByTitle(int page, int size, String title) throws MovieNotFoundException {
        Optional<Page<Movie>> movies= movieRepository.findByTitleContaining(title, PageRequest.of(page, size, Sort.Direction.ASC, "title"));
        if(movies.get().isEmpty()){
            throw new MovieNotFoundException("Movie titles containing: " + title + " do not exist");
        }
        return movies.get();

    }
    @Override
    public Page<String> getActors(int page, int size,   long id) throws MovieNotFoundException {
        List<String> actors=findMovieById(id).getActors().stream().map(x->x.getName()).collect(Collectors.toList());
        return new PageImpl<String>(actors, PageRequest.of(page, size), actors.size());
    }
    @Override
    public Movie updateMovie(@Valid Movie movie, long id) throws MovieNotFoundException {
        Movie foundMovie=movieRepository.findById(id).orElseThrow(()->new MovieNotFoundException("Movie with id: " + id + " does not exist"));
        Set<Actor> actor =foundMovie.getActors();
        movie.setActors(actor);
        movie.setId(id);
        return movieRepository.save(movie);
    }
}
