package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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