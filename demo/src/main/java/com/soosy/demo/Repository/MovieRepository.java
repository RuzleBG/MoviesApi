package com.soosy.demo.Repository;

import org.springframework.stereotype.Repository;

import com.soosy.demo.Entities.Movie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

    Optional<Movie> findByTitle(String title);
    
}
