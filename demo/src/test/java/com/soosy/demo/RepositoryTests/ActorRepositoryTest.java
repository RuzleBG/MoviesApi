package com.soosy.demo.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Repository.ActorRepository;

@DataJpaTest
public class ActorRepositoryTest {
    @Autowired
    private ActorRepository actorRepository;
    
    @BeforeEach
    void setUp(){
        Actor test=new Actor(0L, "Johnathan Smith", 25, null);
        Actor test2=new Actor(0L, "Ethan Smith", 25, null);
        Actor test3=new Actor(0L, "Timothy Smith", 25, null);
        actorRepository.save(test);
        actorRepository.save(test2);
        actorRepository.save(test3);
    }
    @AfterEach
    void tearDown(){
        actorRepository.deleteAll();
    }
    @Test
    public void whenGivenNameShouldFindAllActorsNamesContaining(){
        boolean contains=actorRepository.findByNameContaining("than", PageRequest.of(0, 2)).get().getContent().stream()
        .map(x->x.getName()).allMatch(x->x.contains("than"));
        assertTrue(contains);
    }
    @Test
    public void whenGivenNameShouldNotFindAllActorsNamesContaining(){

        boolean contains=actorRepository.findByNameContaining("than", PageRequest.of(0, 2)).get().getContent().stream()
        .map(x->x.getName()).noneMatch(x->x.contains("athan"));
        assertFalse(contains);
    }

}
