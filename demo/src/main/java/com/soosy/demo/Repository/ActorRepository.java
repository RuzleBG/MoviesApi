package com.soosy.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soosy.demo.Entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long>{

    Optional<Actor> findByName(String actorName);
    
}
