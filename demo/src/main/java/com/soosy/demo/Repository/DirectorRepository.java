package com.soosy.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soosy.demo.Entities.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    
}
