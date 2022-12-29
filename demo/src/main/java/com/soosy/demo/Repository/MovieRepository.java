package com.soosy.demo.Repository;

import org.springframework.stereotype.Repository;

import com.soosy.demo.Entities.Movie;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
    
}
