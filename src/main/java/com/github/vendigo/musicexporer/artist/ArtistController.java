package com.github.vendigo.musicexporer.artist;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ArtistController {

    private static final int PAGE_SIZE = 20;
    private final ArtistRepository artistRepository;

    @GetMapping("/")
    public String artists(Model model) {
        List<Artist> artists = artistRepository.findAll(PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Order.desc("fansCount")))).toList();
        model.addAttribute("artists", artists);
        return "artists";
    }
}
