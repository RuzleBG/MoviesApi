package com.soosy.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.soosy.demo.Entities.Director;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Repository.DirectorRepository;
import com.soosy.demo.Service.DirectorServiceImpl;
import com.soosy.demo.Service.MovieSerivce;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {
    @Mock
    DirectorRepository directorRepository;
    
    @Mock
    MovieSerivce movieSerivce;

    @InjectMocks
    DirectorServiceImpl directorService;
    
    Director director;
    Director director2;
    int page,size;
    String field;

    @BeforeEach
    void setUp(){
        director=new Director(0L, "Kurosawa", 72, new HashSet<>());
        director2=new Director(0L, "Tarantino", 52, new HashSet<>());
        page=0;
        size=2;
        field="id";
    }

    @Test
    public void getAllDirectors(){
        when(directorRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, field))).thenReturn(new PageImpl<>(List.of(director,director2)));
        assertEquals(new PageImpl<>(List.of(director,director2)), directorService.getAllDirectors(page, size, field));
    }
    @Test
    public void shouldFindDirectorByGivenId(){
        when(directorRepository.findById(director.getId())).thenReturn(Optional.of(director));
        assertEquals(director,directorService.getDirectorById(director.getId()));
    }
    @Test
    public void shouldSaveDirector(){
        when(directorRepository.save(Mockito.any(Director.class))).thenReturn(director);
        assertEquals(director, directorService.saveDirector(director));
    }
    @Test
    public void shouldAssignDirector(){
        Movie movie=new Movie(0L, "The shining", 152, null, director);
        when(directorRepository.findById(director.getId())).thenReturn(Optional.of(director));
        when(movieSerivce.findMovieById(Mockito.anyLong())).thenReturn(movie);
        director.getMovies().add(movie);
        assertEquals(director, directorService.assignDirector(director.getId(), 0));
    }
    @Test
    public void shouldGetMoneyByGivenDirectorId(){
        Movie movie=new Movie(0L, "The shining", 152, null, director);
        director.getMovies().add(movie);
        when(directorRepository.findById(director.getId())).thenReturn(Optional.of(director));
        assertEquals(List.of("The shining"), directorService.getMoviesFromDirector(director.getId(), page, size).getContent());
    }
}
