package com.soosy.demo.Service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soosy.demo.Entities.Director;
import com.soosy.demo.Entities.Movie;
import com.soosy.demo.Exceptions.DirectorNotFoundException;
import com.soosy.demo.Exceptions.FieldNotFoundException;
import com.soosy.demo.Exceptions.MovieNotFoundException;
import com.soosy.demo.Repository.DirectorRepository;

import jakarta.validation.Valid;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieSerivce movieSerivce;

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

    @Override
    public Director assignDirector(long directorId, long movieId) throws DirectorNotFoundException, MovieNotFoundException {
        Director director=getDirectorById(directorId);
        Movie movie=movieSerivce.findMovieById(movieId);
        movie.setDirector(director);
        movieSerivce.SaveMovie(movie);
        return director;
    }

    @Override
    public Page<String> getMoviesFromDirector(long directorId, int page, int size) throws DirectorNotFoundException {
        List<String> movieTitles= getDirectorById(directorId).getMovies().stream().map(x->x.getTitle()).collect(Collectors.toList());
        return new PageImpl<String>(movieTitles, PageRequest.of(page,size), movieTitles.size());
    }

    
}
