package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findAll();
}

