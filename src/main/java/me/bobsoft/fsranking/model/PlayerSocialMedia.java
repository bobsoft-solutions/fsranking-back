package me.bobsoft.fsranking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "social_media", schema = "public")
public class PlayerSocialMedia {

    @Id
    @Column(name = "id_social")
    private Integer playerId;

    @Column(name = "facebook")
    private String facebookURL;

    @Column(name = "instagram")
    private String instagramURL;

    @Column(name = "twitter")
    private String twitterURL;

    @Column(name = "youtube")
    private String youtubeURL;

    @Builder
    public PlayerSocialMedia(Integer playerId, String facebookURL, String instagramURL, String twitterURL, String youtubeURL) {
        this.playerId = playerId;
        this.facebookURL = facebookURL;
        this.instagramURL = instagramURL;
        this.twitterURL = twitterURL;
        this.youtubeURL = youtubeURL;
    }
}
