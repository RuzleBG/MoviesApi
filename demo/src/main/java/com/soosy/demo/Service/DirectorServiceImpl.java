package com.soosy.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.Director;
import com.soosy.demo.Exceptions.DirectorNotFoundException;
import com.soosy.demo.Exceptions.FieldNotFoundException;
import com.soosy.demo.Repository.DirectorRepository;

import jakarta.validation.Valid;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public Page<Director> getAllDirectors(int page, int size, String field) throws FieldNotFoundException {
        try{
            return directorRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, field));
        }catch(Exception e){
            throw new FieldNotFoundException("Specified field not found");
        }
    }

    @Override
    public Director getDirectorById(long id) throws DirectorNotFoundException {
        return directorRepository.findById(id).orElseThrow(()->new DirectorNotFoundException("Director with an id of : " + id + " does not exist"));
    }

    @Override
    public Director saveDirector(@Valid Director director) {
        return directorRepository.save(director);
    }

    
}
