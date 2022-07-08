package com.github.vendigo.musicexporer.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT artist.id FROM Artist artist WHERE artist.altPicture IS NULL")
    List<Long> findArtistIdsToLoad();
}
