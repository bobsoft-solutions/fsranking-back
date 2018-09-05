package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.PlayerSocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerSocialMediaRepository extends JpaRepository<PlayerSocialMedia,Integer> {
}