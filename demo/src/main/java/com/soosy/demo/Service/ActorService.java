package com.soosy.demo.Service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Exceptions.ActorNotFoundException;

public interface ActorService {

    List<Actor> getAllActors();

    Actor addNewActor(Actor actor);

    void addMovieToActor(long actorId, long movieId) throws ActorNotFoundException;

    void deleteActor(long actorId) throws ActorNotFoundException;

    Actor findActorById(long actor_id) throws ActorNotFoundException;

    Actor findActorByName(String actorName) throws ActorNotFoundException;
    
}
