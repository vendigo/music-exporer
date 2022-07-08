package com.github.vendigo.musicexporer.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;


@Slf4j
@AllArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/load")
    public void loadImage(@RequestBody PubSubInput input) {
        String encodedData = input.getMessage().getData();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        String decodedString = new String(decodedBytes);
        long artistId = Long.parseLong(decodedString);
        imageService.loadAltImage(artistId);
    }

    @PostMapping("/image/load/submit")
    public void submitMessagesForLoading() {
        imageService.submitMessagesForLoading();
    }

    @Data
    static class PubSubInput {
        private Message message;
    }

    @Data
    static class Message {
        private String data;
    }
}
