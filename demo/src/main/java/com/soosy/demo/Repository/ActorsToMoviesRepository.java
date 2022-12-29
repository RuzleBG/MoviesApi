package com.soosy.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soosy.demo.Entities.ActorToMovie;

@Repository
public interface ActorsToMoviesRepository extends JpaRepository<ActorToMovie, Long>{
    
}
