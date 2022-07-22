package com.github.vendigo.musicexporer.image;

import com.github.vendigo.musicexporer.artist.Artist;
import com.github.vendigo.musicexporer.artist.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.publisher.PubSubPublisherTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class ImageService {

    private final ArtistRepository artistRepository;
    private final UploadService uploadService;
    private final PubSubPublisherTemplate pubSubPublisher;

    @Value("${pubsub.imagesToLoad.topic.name}")
    private String topicName;

    @Transactional
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
        byte[] fileBytes = loadFile(imgUrl);
        String imageGsUrl = uploadService.uploadFile(buildFileName(artistId, imgUrl), fileBytes);
        log.info("File uploaded to: {}", imageGsUrl);
        artist.setAltPicture(imageGsUrl);
        artistRepository.save(artist);
    }

    @SneakyThrows
    private static byte[] loadFile(String fileUrl) {
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    private static String buildFileName(long artistId, String imgUrl) {
        String[] parts = imgUrl.split("\\.");
        return String.format("artist-%d.%s", artistId, parts[parts.length - 1]);
    }

    public void submitMessagesForLoading() {
        artistRepository.findArtistIdsToLoad()
                .forEach(artistId -> pubSubPublisher.publish(topicName, artistId.toString().getBytes()));
    }
}
