package me.bobsoft.fsranking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "location")
public class Location {

    @Id
    @Column(name = "id_location")
    private Integer id;

    @Column(name = "name")
    private String name;
}
