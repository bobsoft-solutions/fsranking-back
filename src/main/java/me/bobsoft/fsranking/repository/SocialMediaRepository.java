package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Integer> {}
