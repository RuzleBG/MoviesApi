package com.soosy.demo.Services;

import static org.mockito.Mockito.verify;

import javax.print.attribute.standard.PageRanges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.soosy.demo.Repository.ActorRepository;
import com.soosy.demo.Service.ActorService;
import com.soosy.demo.Service.ActorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;
    private ActorService actorService;

    @BeforeEach
    void setUp(){
        actorService=new ActorServiceImpl(actorRepository);
    }
    @Test
    public void testGetAllActors(){
        actorService.getAllActors(0, 3, "name");
        verify(actorRepository).findAll(PageRequest.of(0, 3, Sort.Direction.ASC, "name"));
    }
}
