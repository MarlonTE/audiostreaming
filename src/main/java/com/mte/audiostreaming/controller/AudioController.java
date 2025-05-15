package com.mte.audiostreaming.controller;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mte.audiostreaming.model.AudioFile;
import com.mte.audiostreaming.repository.AudioRepository;
import com.mte.audiostreaming.service.AudioService;

import org.springframework.core.io.Resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(defaultValue = "true") boolean asc) {
        boolean useGlobalSearch = term != null && !term.isEmpty();
        boolean useSorting = SortBy != null && !SortBy.isEmpty();

        if (useGlobalSearch && !useSorting) {
            return audioService.search(
                    term,
                    name,
                    album,
                    artist,
                    genre,
                    year);
        } else {
            return audioService.searchAudioFiles(
                    name,
                    album,
                    artist,
                    genre,
                    year,
                    SortBy,
                    asc);
        }
    }

    @Autowired
    private AudioRepository audioRepository;

    @GetMapping("/audio/{id}")
    public ResponseEntity<Resource> streamAudio(@PathVariable Long id) {
        AudioFile audioFile = audioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audio not found"));

        Path path = Paths.get(audioFile.getAudioPath());
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(resource)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resource);
    }

}
