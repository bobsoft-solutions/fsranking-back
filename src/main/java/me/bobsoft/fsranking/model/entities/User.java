package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "privilage")
    private String privilage;

    @Column(name = "password")
    private String password;

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.mail = user.getMail();
        this.privilage = user.getPrivilage();
        this.password = user.getPassword();
    }
}