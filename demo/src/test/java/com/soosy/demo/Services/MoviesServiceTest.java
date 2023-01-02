package com.soosy.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Repository.MovieRepository;
import com.soosy.demo.Service.MovieServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MoviesServiceTest {
    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;
    Movie movie;
    Movie movie2;
    int page,size;
    String field;

    @BeforeEach
    void setUp(){
        movie=new Movie(0L, "The shining", 152, null, null);
        movie2=new Movie(1l, "Raiders of The lost arc", 94, null, null);
        page=0;
        size=2;
        field="id";
    }

    @Test
    public void testGetAllMovies(){
        when(movieRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, field))).thenReturn(new PageImpl<>(List.of(movie,movie2)));
        assertFalse(movieService.getAllMovies(page, size, field).isEmpty());
    }
    @Test
    public void saveMovie(){
        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);
        assertEquals(movieService.SaveMovie(movie), movie);
    }
    @Test
    public void findMovieById(){
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        assertEquals(movieService.findMovieById(movie.getId()), movie);
    }
    @Test
    public void doNotGetMovieById(){
        when(movieRepository.findById(movie.getId())).thenThrow(new MovieNotFoundException("Movie not found"));
        RuntimeException exception = assertThrows(MovieNotFoundException.class,()->movieService.findMovieById(movie.getId()));
        assertEquals(exception.getClass(), MovieNotFoundException.class);
    }
    @Test
    public void shouldFindMoviesWithTitleContaining(){
        when(movieRepository.findByTitleContaining("The", PageRequest.of(page,size,Sort.Direction.ASC,"title")))
        .thenReturn(Optional.of(new PageImpl<>(List.of(movie,movie2))));
        assertEquals(new PageImpl<>(List.of(movie, movie2)), movieService.findMovieByTitle(page, size, "The"));
    }
    @Test
    public void shouldReturnActors(){
        movie.setActors(Set.of(new Actor(0L, "John", 52, Set.of(movie))));
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        assertEquals(List.of("John"), movieService.getActors(page, size, movie.getId()).getContent());
    }
    @Test
    public void shouldUpdateMovie(){
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie2);
        assertEquals(movieService.updateMovie(movie2, movie.getId()), movie2);
    }
}
