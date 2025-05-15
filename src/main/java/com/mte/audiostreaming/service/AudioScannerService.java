package com.mte.audiostreaming.service;

import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mte.audiostreaming.model.AudioFile;
import com.mte.audiostreaming.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Service
public class AudioScannerService {

    @Autowired
    private AudioRepository audioRepository;

    public void scanDirectory(String path) {
        File root = new File(path);
        if (!root.exists() || !root.isDirectory()) {
            System.err.println("src invalid: " + path);
            return;
        }
        scanRecursive(root);
    }

    private void scanRecursive(File dir) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanRecursive(file);
            } else if (file.getName().toLowerCase().endsWith(".mp3")) {
                try {
                    Mp3File mp3file = new Mp3File(file);
                    String title = file.getName();
                    String artist = "unknown";
                    String album = "unknown";
                    String genre = "unknown";
                    int year = 0;

                    if (mp3file.hasId3v2Tag()) {
                        ID3v2 tag = mp3file.getId3v2Tag();
                        title = tag.getTitle() != null ? tag.getTitle() : file.getName();
                        artist = tag.getArtist() != null ? tag.getArtist() : "unknown";
                        album = tag.getAlbum() != null ? tag.getAlbum() : "unknown";
                        genre = tag.getGenreDescription() != null ? tag.getGenreDescription() : "unknown";
                        year = parseYear(tag.getYear());
                    } else if (mp3file.hasId3v1Tag()) {
                        ID3v1 tag = mp3file.getId3v1Tag();
                        title = tag.getTitle() != null ? tag.getTitle() : file.getName();
                        artist = tag.getArtist() != null ? tag.getArtist() : "unknown";
                        album = tag.getAlbum() != null ? tag.getAlbum() : "unknown";
                        genre = tag.getGenreDescription() != null ? tag.getGenreDescription() : "unknown";
                        year = parseYear(tag.getYear());
                    }

                    AudioFile audioFile = new AudioFile();
                    audioFile.setName(title);
                    audioFile.setAlbum(album);
                    audioFile.setArtists(Arrays.stream(artist
                        .split(","))
                        .map(String::trim)
                        .toList());
                    audioFile.setGenres(Arrays.stream(genre
                        .split(","))
                        .map(String::trim)
                        .toList());
                    audioFile.setYear(year);
                    audioFile.setAudioPath(file.getAbsolutePath());

                    audioRepository.save(audioFile);

                    System.out.println("✔ Added: " + title);
                } catch (Exception e) {
                    System.err.println("✘ Error: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }

    private int parseYear(String yearStr) {
        try {
            return Integer.parseInt(yearStr);
        } catch (Exception e) {
            return 0;
        }
    }
}
