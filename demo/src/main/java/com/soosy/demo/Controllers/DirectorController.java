package com.soosy.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Entities.Director;
import com.soosy.demo.Exceptions.DirectorNotFoundException;
import com.soosy.demo.Exceptions.FieldNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Service.DirectorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/directors")
public class DirectorController {
    @Autowired
    DirectorService directorService;

    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<Director>> getAllDirectors(@PathVariable int page, @PathVariable int size, 
    @RequestParam(value = "field") String field) throws FieldNotFoundException{
        return new ResponseEntity<List<Director>>(directorService.getAllDirectors(page, size, field).getContent(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable long id) throws DirectorNotFoundException{
        return new ResponseEntity<Director>(directorService.getDirectorById(id), HttpStatus.OK);
    }
    
    @PostMapping("/save")
    public ResponseEntity<Director> saveDirector(@Valid @RequestBody Director director){
        return new ResponseEntity<Director>(directorService.saveDirector(director), HttpStatus.OK);
    }
    @PutMapping("/assigndirector")
    public ResponseEntity<HttpStatus> assignDirector(@RequestParam(value = "director_id") long directorId, 
    @RequestParam(value = "movie_id") long movieId) throws DirectorNotFoundException, MovieNotFoundException{
        directorService.assignDirector(directorId, movieId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    @GetMapping("/getmoviesfromdirector/{page}/{size}")
    public ResponseEntity<List<String>> directorMovies(@RequestParam(value = "director_id") long directorId, @PathVariable int page, 
    @PathVariable int size) throws DirectorNotFoundException{
        return new ResponseEntity<List<String>>(directorService.getMoviesFromDirector(directorId, page, size).getContent(), HttpStatus.OK);
    }
}
