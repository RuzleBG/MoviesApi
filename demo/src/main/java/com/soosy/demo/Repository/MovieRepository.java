package com.soosy.demo.Repository;

import org.springframework.stereotype.Repository;

import com.soosy.demo.Entities.Movie;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

    Optional<Page<Movie>> findByTitleContaining(String title, PageRequest pageRequest);
    
}
