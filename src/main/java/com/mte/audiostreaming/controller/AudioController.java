package com.mte.audiostreaming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mte.audiostreaming.model.AudioFile;
import com.mte.audiostreaming.service.AudioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/audio")
@CrossOrigin(origins = "*")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @GetMapping("search")
    public List<AudioFile> search(
        @RequestParam(required = false) String term,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String album,
        @RequestParam(required = false) String artist,
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) String SortBy,
        @RequestParam(defaultValue = "true") boolean asc
    )  {
        boolean useGlobalSearch = term != null && !term.isEmpty();
        boolean useSorting = SortBy != null && !SortBy.isEmpty();

        if (useGlobalSearch && !useSorting) {
            return audioService.search(
                term,
                name,
                album,
                artist,
                genre,
                year
            );
        } else {
            return audioService.searchAudioFiles(
                name,
                album,
                artist,
                genre,
                year,
                SortBy,
                asc
            );
        }
    }
}
