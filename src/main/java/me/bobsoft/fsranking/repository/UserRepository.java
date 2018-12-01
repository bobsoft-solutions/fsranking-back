package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByMail(String userEmail);

    List<User> findAll();
}
