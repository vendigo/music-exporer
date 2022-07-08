package com.github.vendigo.musicexporer.image;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/load")
    public void loadImage(@RequestBody PubsubMessage message) {
        ByteString data = message.getData();
        String receivedData = data.toString();
        log.info("Received: " + receivedData);
        //imageService.loadAltImage(artistId);
    }
}
