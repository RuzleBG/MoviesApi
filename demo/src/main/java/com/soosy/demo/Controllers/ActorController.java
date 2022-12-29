package com.soosy.demo.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Exceptions.ActorNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Service.ActorService;

import jakarta.validation.Valid;

@RestController
public class ActorController {
    
    @Autowired
    ActorService actorService;

    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> getAllActors(){
        return new ResponseEntity<List<Actor>>(actorService.getAllActors(), HttpStatus.OK);
    }
    @PostMapping("/actors")
    public ResponseEntity<Actor> addNewActor(@Valid @RequestBody Actor actor){
        return new ResponseEntity<Actor>(actorService.addNewActor(actor), HttpStatus.OK);
    }
    @GetMapping("/actors/findById")
    public ResponseEntity<Actor> findActorById(@RequestParam(value = "actor_id") long actor_id) throws ActorNotFoundException{
        return new ResponseEntity<Actor>(actorService.findActorById(actor_id), HttpStatus.OK);
    }
    @PostMapping("/addmovietoactor")
    public ResponseEntity<HttpStatus> addMovieToAnActor(@RequestParam(value = "actor_id") long actorId, 
        @RequestParam(value = "movie_id") long movieId) throws ActorNotFoundException, MovieNotFoundException{
            System.out.println(actorId+ " " + movieId);
            actorService.addMovieToActor(actorId, movieId);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
    @DeleteMapping("/actors")
    public ResponseEntity<HttpStatus> deleteActor(@RequestParam(value  = "actor_id") long actorId) throws ActorNotFoundException{
        actorService.deleteActor(actorId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    @GetMapping("/actors/name")
    public ResponseEntity<Actor> findActorByName(@RequestParam(value = "actor_name") String actorName) throws ActorNotFoundException{
        return new ResponseEntity<Actor>(actorService.findActorByName(actorName), HttpStatus.OK);
    }
}
