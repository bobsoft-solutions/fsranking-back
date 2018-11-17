package me.bobsoft.fsranking.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}