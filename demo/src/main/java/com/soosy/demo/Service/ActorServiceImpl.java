package com.soosy.demo.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.ActorNotFoundException;
import com.soosy.demo.Exceptions.FieldNotFoundException;
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
    public Page<Actor> getAllActors(int page, int size, String field) throws InvalidFileNameException {
        try {
            return actorRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, field));
        } catch (Exception e) {
            throw new FieldNotFoundException("Invalid Field Name");
        }
    }
    @Override
    public Actor addNewActor(Actor actor) {
        return actorRepository.save(actor);
    }
    @Override
    public void addMovieToActor(long actorId, long movieId) throws ActorNotFoundException, MovieNotFoundException {
        Actor actor=findActorById(actorId);
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
    public Page<Actor> findActorByName(String actorName, int page, int size) throws ActorNotFoundException {
        Optional<Page<Actor>> actors=actorRepository.findByNameContaining(actorName, PageRequest.of(page, size, Sort.Direction.ASC, "name"));
        if(actors.get().isEmpty()){
            throw new ActorNotFoundException("Actor with name: " + actorName+ " cannot be found");
        }
        return actors.get();
    }
    @Override
    public Page<String> getAllMoviesByAnActor(long actorId, int page, int size) throws ActorNotFoundException {
        Optional<Actor> actor= actorRepository.findById(actorId);
        if(!actor.isPresent()){
            throw new ActorNotFoundException("Actor with id: " + actorId + " cannot be found");
        }
        List<String> movies=actor.get().getMovies().stream().map(x->x.getTitle()).collect(Collectors.toList());
        return new PageImpl<String>(movies,PageRequest.of(page, size),movies.size());

    }
    @Override
    public Actor updateActor(@Valid Actor actor, long actorId) {
        actor.setId(actorId);
        actor.setMovies(findActorById(actorId).getMovies());
        return actorRepository.save(actor);
    }   
}
