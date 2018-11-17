package me.bobsoft.fsranking.model.player;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "social_media", schema = "public")
public class PlayerSocialMedia {

    @Id
    @Column(name = "id_player")
    private Integer playerId;

    @Column(name = "facebook")
    private String facebookURL;

    @Column(name = "instagram")
    private String instagramURL;

    @Column(name = "youtube")
    private String youtubeURL;

    @Builder
    public PlayerSocialMedia(Integer playerId, String facebookURL, String instagramURL, String youtubeURL) {
        this.playerId = playerId;
        this.facebookURL = facebookURL;
        this.instagramURL = instagramURL;
        this.youtubeURL = youtubeURL;
    }
}
