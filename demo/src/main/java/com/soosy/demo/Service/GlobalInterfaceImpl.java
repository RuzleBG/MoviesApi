package com.soosy.demo.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.ActorToMovie;
import com.soosy.demo.Repository.ActorsToMoviesRepository;

@Service
public class GlobalInterfaceImpl implements GlobalService{

    @Autowired
    ActorService actorService;

    @Autowired
    MovieSerivce movieSerivce;

    @Autowired
    ActorsToMoviesRepository actorsToMoviesRepository;

    @Override
    public Map<String, String> getActorsToMoives() {
        Map<String, String> map= new HashMap<>();
        List<ActorToMovie> actorsToMoviesList=actorsToMoviesRepository.findAll(Sort.by(Sort.Direction.ASC, "actorId"));
        actorsToMoviesList.stream().forEach(x->{
            map.put(actorService.findActorById(x.getActorId()).getName(),movieSerivce.findMovieById(x.getMovieId()).getTitle());
        });
        
        return map;
    }
    
}
