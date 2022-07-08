package com.github.vendigo.musicexporer.image;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/load")
    public void loadImage(@RequestParam("artistId") long artistId) {
        imageService.loadAltImage(artistId);
    }
}
