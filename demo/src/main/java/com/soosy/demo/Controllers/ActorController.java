package com.soosy.demo.Controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Exceptions.ActorNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Service.ActorService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/actors")
public class ActorController {
    
    @Autowired
    ActorService actorService;

    @GetMapping()
    public ResponseEntity<List<Actor>> getAllActors(){
        return new ResponseEntity<List<Actor>>(actorService.getAllActors(), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Actor> addNewActor(@Valid @RequestBody Actor actor){
        return new ResponseEntity<Actor>(actorService.addNewActor(actor), HttpStatus.OK);
    }
    @GetMapping("/findById")
    public ResponseEntity<Actor> findActorById(@RequestParam(value = "actor_id") long actor_id) throws ActorNotFoundException{
        return new ResponseEntity<Actor>(actorService.findActorById(actor_id), HttpStatus.OK);
    }
    @PostMapping("/addmovietoactor")
    public ResponseEntity<HttpStatus> addMovieToAnActor(@RequestParam(value = "actor_id") long actorId, 
        @RequestParam(value = "movie_id") long movieId) throws ActorNotFoundException, MovieNotFoundException{
            actorService.addMovieToActor(actorId, movieId);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteActor(@RequestParam(value  = "actor_id") long actorId) throws ActorNotFoundException{
        actorService.deleteActor(actorId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    @GetMapping("/name")
    public ResponseEntity<Actor> findActorByName(@RequestParam(value = "actor_name") String actorName) throws ActorNotFoundException{
        return new ResponseEntity<Actor>(actorService.findActorByName(actorName), HttpStatus.OK);
    }
    @GetMapping("/listofmovies")
    public ResponseEntity<Set<String>> getAllMoviesByAnActor(@RequestParam(value="actor_id") long actorId) throws ActorNotFoundException{
        return new ResponseEntity<Set<String>>(actorService.getAllMoviesByAnActor(actorId), HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<Actor> updateActor(@Valid @RequestBody Actor actor, @RequestParam(value = "actor_id") long actorId){
        return new ResponseEntity<Actor>(actorService.updateActor(actor, actorId), HttpStatus.OK);
    }
}
