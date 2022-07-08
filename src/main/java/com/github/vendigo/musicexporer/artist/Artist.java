package com.github.vendigo.musicexporer.artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dez_artist")
public class Artist {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String picture;
    @Column(name = "alt_picture")
    private String altPicture;
    @Column(name = "nb_fans")
    private Integer fansCount;
}
