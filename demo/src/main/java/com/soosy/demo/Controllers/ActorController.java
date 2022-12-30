package com.soosy.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Entities.Actor;
import com.soosy.demo.Exceptions.ActorNotFoundException;
import com.soosy.demo.Exceptions.FieldNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Service.ActorService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/actors")
public class ActorController {
    
    @Autowired
    ActorService actorService;

    @GetMapping("/{page}/{size}/{field}")
    public ResponseEntity<List<Actor>> getAllActors(@PathVariable(value = "page") int page, 
        @PathVariable(value = "size") int size, @PathVariable(value = "field") String field) throws FieldNotFoundException{
        return new ResponseEntity<List<Actor>>(actorService.getAllActors(page,size,field).getContent(), HttpStatus.OK);
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
    @GetMapping("/name/{page}/{size}")
    public ResponseEntity<List<Actor>> findActorByName(@RequestParam(value = "actor_name") String actorName,
        @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) throws ActorNotFoundException{
        return new ResponseEntity<List<Actor>>(actorService.findActorByName(actorName, page, size).getContent(), HttpStatus.OK);
    }
    @GetMapping("/listofmovies/{page}/{size}")
    public ResponseEntity<List<String>> getAllMoviesByAnActor(@RequestParam(value="actor_id") long actorId, 
        @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) throws ActorNotFoundException{
        return new ResponseEntity<List<String>>(actorService.getAllMoviesByAnActor(actorId,page,size).getContent(), HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<Actor> updateActor(@Valid @RequestBody Actor actor, @RequestParam(value = "actor_id") long actorId){
        return new ResponseEntity<Actor>(actorService.updateActor(actor, actorId), HttpStatus.OK);
    }
}
