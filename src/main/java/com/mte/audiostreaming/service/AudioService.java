package com.mte.audiostreaming.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import static com.mte.audiostreaming.specification.AudioFileSpecification.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import com.mte.audiostreaming.model.AudioFile;
import com.mte.audiostreaming.repository.AudioRepository;

@Service
public class AudioService {

    @Autowired
    private AudioRepository audioRepository;

    public List<AudioFile> searchAudioFiles(
            String name,
            String album,
            String artist,
            String genre,
            Integer year,
            String sortBy,
            boolean asc) {
        Specification<AudioFile> spec = Specification
                .where(nameContains(name))
                .and(albumContains(album))
                .and(artistContains(artist))
                .and(genreContains(genre))
                .and(yearEquals(year));

        String sortField = (sortBy == null || sortBy.isBlank()) ? "name" : sortBy;
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        return audioRepository.findAll(spec, sort);
    }

    public List<AudioFile> search(
            String term,
            String name,
            String album,
            String artist,
            String genre,
            Integer year) {
        Specification<AudioFile> spec = Specification
                .where(globalTextSearch(term))
                .and(nameContains(name))
                .and(albumContains(album))
                .and(artistContains(artist))
                .and(genreContains(genre))
                .and(yearEquals(year));

        return audioRepository.findAll(spec);
    }

    public void scanAndUpdateAudioFiles(Path directory) {
        try (Stream<Path> paths = Files.walk(directory, Integer.MAX_VALUE)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".mp3"))
                    .forEach(path -> {
                        try {
                            Mp3File mp3file = new Mp3File(path.toFile());
                            String filePath = path.toAbsolutePath().toString();

                            AudioFile audioFile = audioRepository.findByPath(filePath)
                                    .orElse(new AudioFile());

                            audioFile.setAudioPath(filePath);
                            audioFile.setName(path.getFileName().toString());
                            audioFile.setSize(Files.size(path));

                            if (mp3file.hasId3v2Tag()) {
                                ID3v2 tag = mp3file.getId3v2Tag();
                                audioFile.setArtists(List.of(tag.getArtist()));
                                audioFile.setGenres(List.of(tag.getGenreDescription()));
                                audioFile.setAlbum(tag.getAlbum());
                                audioFile.setYear(parseYear(tag.getYear()));
                                audioFile.setDuration(mp3file.getLengthInSeconds());
                            }

                            audioRepository.save(audioFile);

                        } catch (Exception e) {
                            System.err.println("Error: " + path + " - " + e.getMessage());
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer parseYear(String yearString) {
        try {
            return Integer.parseInt(yearString);
        } catch (Exception e) {
            return null;
        }
    }

}
