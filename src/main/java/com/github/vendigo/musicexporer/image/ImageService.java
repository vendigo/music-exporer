package com.github.vendigo.musicexporer.image;

import org.springframework.stereotype.Service;

import com.github.vendigo.musicexporer.artist.Artist;
import com.github.vendigo.musicexporer.artist.ArtistRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ImageService {

    private final ArtistRepository artistRepository;

    public void loadAltImage(long artistId) {
        log.info("Checking image for artistId: {}", artistId);
        Artist artist = artistRepository.findById(artistId)
            .orElseThrow(() -> new IllegalArgumentException("Not existent artistId"));

        if (artist.getAltPicture() != null) {
            log.info("Alternative image is already loaded, skipping");
            return;
        }

        String imgUrl = artist.getPicture();
        log.info("Loading image: {}", imgUrl);
    }
}
