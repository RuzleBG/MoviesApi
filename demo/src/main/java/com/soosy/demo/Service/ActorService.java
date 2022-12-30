package com.soosy.demo.Service;

import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.data.domain.Page;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Exceptions.ActorNotFoundException;

import jakarta.validation.Valid;

public interface ActorService {

    Page<Actor> getAllActors(int page, int size, String field) throws InvalidFileNameException;

    Actor addNewActor(Actor actor);

    void addMovieToActor(long actorId, long movieId) throws ActorNotFoundException;

    void deleteActor(long actorId) throws ActorNotFoundException;

    Actor findActorById(long actor_id) throws ActorNotFoundException;

    Page<Actor> findActorByName(String actorName, int page, int size) throws ActorNotFoundException;

    Page<String> getAllMoviesByAnActor(long actorId, int page, int size) throws ActorNotFoundException;

    Actor updateActor(@Valid Actor actor, long actorId);
    
}
