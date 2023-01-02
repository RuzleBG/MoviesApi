package com.soosy.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Repository.ActorRepository;
import com.soosy.demo.Service.ActorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;
    @InjectMocks
    private ActorServiceImpl actorService;
    Actor test;
    Actor actor;

    @BeforeEach
    void setUp(){
        test=new Actor(0L, "Johnathan Smith", 25, null);
        actor=new Actor(0L, "John", 18, null);
    }
    @Test
    public void testGetAllActors(){
        actorService.getAllActors(0, 3, "name");
        verify(actorRepository).findAll(PageRequest.of(0, 3, Sort.Direction.ASC, "name"));
    }   
    @Test
    public void canSaveActor(){
        actorService.addNewActor(test);
        ArgumentCaptor<Actor> actorArgumentCapture=ArgumentCaptor.forClass(Actor.class);
        verify(actorRepository).save(actorArgumentCapture.capture());
        Actor actor=actorArgumentCapture.getValue();
        assertEquals(actor, test);
    }

    @Test
    public void byGivenIdFindActor(){
        long id=0;
        Optional<Actor> optional=Optional.of(actor);
        when(actorRepository.findById(id)).thenReturn(optional);
        assertEquals(actor, actorService.findActorById(id));

    }

    @Test
    public void byGivenNameFindActor(){
        String name="John";
        when(actorRepository.findByNameContaining(name, PageRequest.of(0, 5, Sort.Direction.ASC, "name"))).thenReturn(Optional.of(new PageImpl<>(List.of(actor))));
        assertEquals(actorService.findActorByName(name, 0, 5), new PageImpl<>(List.of(actor)));
    }
    @Test
    public void whenGivenActorIdShouldReturnListOfMovies(){
        actor.setMovies(Set.of(new Movie(0L, "The shining", 152, Set.of(actor),null)));
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        assertEquals(actorService.getAllMoviesByAnActor(actor.getId(), 0, 1).getContent(), List.of("The shining"));
    }
    @Test
    public void whenGivenActorIdShouldNotReturnListOfMovies(){
        actor.setMovies(Set.of());
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        assertTrue(actorService.getAllMoviesByAnActor(actor.getId(), 0, 5).isEmpty());
    }
    @Test
    public void whenGivenNewActorShouldUpdate(){
        when(actorRepository.findById(0L)).thenReturn(Optional.of(actor));
        Actor newActor=new Actor(0L, "Steve", 18, Set.of(new Movie(0L, "Star wars", 152, Set.of(actor), null)));;
        when(actorRepository.save(actor)).thenReturn(newActor);
        assertEquals(actorService.updateActor(actor, 0L), newActor);
    }
}
