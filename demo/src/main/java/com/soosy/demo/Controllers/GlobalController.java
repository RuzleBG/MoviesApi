package com.soosy.demo.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soosy.demo.Service.GlobalService;

@RestController
public class GlobalController {
    @Autowired
    GlobalService globalService;

    @GetMapping("/actorstomovies")
    public ResponseEntity<Map<String,String>> getActorsToMovies(){
        return new ResponseEntity<Map<String,String>>(globalService.getActorsToMoives(), HttpStatus.OK);
    }
}
