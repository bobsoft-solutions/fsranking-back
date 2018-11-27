package me.bobsoft.fsranking.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "social_media")
public class SocialMedia {

    @Id
    @Column(name = "id_player")
    private Integer playerId;

    @Column(name = "facebook")
    private String facebookURL;

    @Column(name = "instagram")
    private String instagramURL;

    @Column(name = "youtube")
    private String youtubeURL;
}
