package com.soosy.demo.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Repository.MovieRepository;

@DataJpaTest
public class MoviesRepositoryTest {
    @Autowired
    MovieRepository movieRepository;
    PageRequest pageRequest=PageRequest.of(0, 5);

    @BeforeEach
    void setUp(){
        movieRepository.save(new Movie(0L,"The shining",152,null,null));
        movieRepository.save(new Movie(0L,"The Amazing spiderman 2",152,null,null));
    }
    @AfterEach
    void tearDown(){
        movieRepository.deleteAll();
    }

    @Test
    public void ifGivenTitleFindAllMoviesWithThatContainTitle(){
        String title="The";
        boolean valid=movieRepository.findByTitleContaining(title, pageRequest).get().stream().map(x->x.getTitle()).allMatch(x->x.contains(title));
        assertTrue(valid);
    }
    @Test
    public void ifGivenTitleFindNoMoviesWithThatContainTitle(){
        String title="The";
        boolean valid=movieRepository.findByTitleContaining(title, pageRequest).get().stream().map(x->x.getTitle()).noneMatch(x->x.contains(title));
        assertFalse(valid);
    }
}
