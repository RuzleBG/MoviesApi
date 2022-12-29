package com.soosy.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actors_and_films")
public class ActorToMovie {
    @Id
    @Column(name = "actor_id", updatable = false)
    private int actorId;
    
    @Column(name="movie_id", updatable = false)
    private int movieId;
}
