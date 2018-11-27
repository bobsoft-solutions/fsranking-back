package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "competition")
public class Competition {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "id_location")
    private Location location;

    @Column(name = "year")
    private ZonedDateTime year;

    @Column(name = "importance")
    private int importance;
}