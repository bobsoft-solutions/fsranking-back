package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "default_point")
public class DefaultPoint {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private Integer value;
}
