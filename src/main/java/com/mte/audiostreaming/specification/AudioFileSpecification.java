package com.mte.audiostreaming.specification;

import org.springframework.data.jpa.domain.Specification;
import com.mte.audiostreaming.model.AudioFile;

import java.util.Arrays;

public class AudioFileSpecification {

    @SuppressWarnings("unused")
    public static Specification<AudioFile> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank())
                return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    @SuppressWarnings("unused")
    public static Specification<AudioFile> albumContains(String album) {
        return (root, query, cb) -> {
            if (album == null || album.isBlank())
                return null;
            return cb.like(cb.lower(root.get("album")), "%" + album.toLowerCase() + "%");
        };
    }

    @SuppressWarnings("unused")
    public static Specification<AudioFile> artistContains(String artist) {
        return (root, query, cb) -> {
            if (artist == null || artist.isBlank())
                return null;
            return cb.isMember(artist.toLowerCase(), root.get("artists"));
        };
    }

    @SuppressWarnings("unused")
    public static Specification<AudioFile> genreContains(String genre) {
        return (root, query, cb) -> {
            if (genre == null || genre.isBlank())
                return null;
            return cb.isMember(genre.toLowerCase(), root.get("genres"));
        };
    }

    @SuppressWarnings("unused")
    public static Specification<AudioFile> yearEquals(Integer year) {
        return (root, query, cb) -> year == null ? null : cb.equal(root.get("year"), year);
    }

    @SuppressWarnings("unused")
    public static Specification<AudioFile> globalTextSearch(String term) {
        return (root, query, cb) -> {
            if (term == null || term.isBlank())
                return null;

            String likeTerm = "%" + term.toLowerCase() + "%";
            String exactTerm = term.toLowerCase();

            return cb.or(
                    cb.like(cb.lower(root.get("name")), likeTerm),
                    cb.like(cb.lower(root.get("album")), likeTerm),
                    cb.isMember(exactTerm, root.get("artists")),
                    cb.isMember(exactTerm, root.get("genres")));
        };
    }

    public static void processAudioFile(AudioFile audioFile, String artist, String genre) {
        audioFile.setArtists(Arrays.stream(artist
                .split(","))
                .map(s -> s.trim().toLowerCase())
                .toList());
        audioFile.setGenres(Arrays.stream(genre
                .split(","))
                .map(s -> s.trim().toLowerCase())
                .toList());
    }
}
