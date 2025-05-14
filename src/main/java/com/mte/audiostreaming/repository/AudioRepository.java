package com.mte.audiostreaming.repository;

import com.mte.audiostreaming.model.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioRepository extends JpaRepository<AudioFile, Long> {

    // Búsquedas
    List<AudioFile> findByNameContainingIgnoreCase(String name);
    List<AudioFile> findByArtistsContainingIgnoreCase(String artist);
    List<AudioFile> findByGenresContainingIgnoreCase(String genre);
    List<AudioFile> findByAlbumContainingIgnoreCase(String album);
    List<AudioFile> findByYear(int year);

    // Ordenamientos válidos (por campos simples)
    List<AudioFile> findAllByOrderByNameAsc();
    List<AudioFile> findAllByOrderByNameDesc();
    List<AudioFile> findAllByOrderByAlbumAsc();
    List<AudioFile> findAllByOrderByAlbumDesc();
    List<AudioFile> findAllByOrderByYearAsc();
    List<AudioFile> findAllByOrderByYearDesc();
    List<AudioFile> findAllByOrderByDurationAsc();
    List<AudioFile> findAllByOrderByDurationDesc();
    List<AudioFile> findAllByOrderByTrackNumberAsc();
    List<AudioFile> findAllByOrderByTrackNumberDesc();

    // Los de artist y genre se comentan por ser listas
    // List<AudioFile> findAllByOrderByArtistAsc(); ❌
    // List<AudioFile> findAllByOrderByGenreAsc();  ❌
}
