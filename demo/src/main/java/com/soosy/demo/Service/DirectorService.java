package com.soosy.demo.Service;

import org.springframework.data.domain.Page;

import com.soosy.demo.Entities.Director;
import com.soosy.demo.Exceptions.DirectorNotFoundException;
import com.soosy.demo.Exceptions.FieldNotFoundException;

import jakarta.validation.Valid;

public interface DirectorService {

    Page<Director> getAllDirectors(int page, int size, String field) throws FieldNotFoundException;

    Director getDirectorById(long id) throws DirectorNotFoundException;

    Director saveDirector(@Valid Director director);
    
}
