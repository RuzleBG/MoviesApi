package com.soosy.demo.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.ActorNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Repository.ActorRepository;

import jakarta.validation.Valid;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieSerivce movieSerivce;

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }
    @Override
    public Actor addNewActor(Actor actor) {
        return actorRepository.save(actor);
    }
    @Override
    public void addMovieToActor(long actorId, long movieId) throws ActorNotFoundException, MovieNotFoundException {
        Actor actor=actorRepository.findById(actorId).orElseThrow(()->new ActorNotFoundException("Actor not found"));
        Movie movie=movieSerivce.findMovieById(movieId);
        actor.getMovies().add(movie);
        movie.getActors().add(actor);
        actorRepository.save(actor);
    }
    @Override
    public void deleteActor(long actorId) throws ActorNotFoundException {
        Actor actor=actorRepository.findById(actorId).orElseThrow(()->new ActorNotFoundException("Actor not found"));
        actor.getMovies().stream().forEach(x->x.getActors().clear());
        actor.getMovies().clear();        
        actorRepository.deleteById(actorId);
        
    }
    @Override
    public Actor findActorById(long actorId) throws ActorNotFoundException {
        return actorRepository.findById(actorId).orElseThrow(()->new ActorNotFoundException("Actor not found"));
    }
    @Override
    public Actor findActorByName(String actorName) throws ActorNotFoundException {
        return actorRepository.findByName(actorName).orElseThrow(()->new ActorNotFoundException("Actor with name: " + actorName+ " cannot be found"));
    }
    @Override
    public Set<String> getAllMoviesByAnActor(long actorId) throws ActorNotFoundException {
        Optional<Actor> actor= actorRepository.findById(actorId);
        if(!actor.isPresent()){
            throw new ActorNotFoundException("Actor with id: " + actorId + " cannot be found");
        }
        return actor.get().getMovies().stream().map(x->x.getTitle()).collect(Collectors.toSet());

    }
    @Override
    public Actor updateActor(@Valid Actor actor, long actorId) {
        actor.setId(actorId);
        actor.setMovies(findActorById(actorId).getMovies());
        return actorRepository.save(actor);
    }   
}
