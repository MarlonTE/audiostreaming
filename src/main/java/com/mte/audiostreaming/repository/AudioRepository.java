package com.mte.audiostreaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mte.audiostreaming.model.AudioFile;
import java.util.List;


@Repository
public interface AudioRepository extends JpaRepository<AudioFile, Long> {

    List<AudioFile> findByName(String name);
    List<AudioFile> findByAlbum(String album);
    List<AudioFile> findByArtist(String artist);
    List<AudioFile> findByGenre(String genre);
    List<AudioFile> findByYear(int year);

    List<AudioFile> sortListByNameAsc();
    List<AudioFile> sortListByNameDesc();
    List<AudioFile> sortListByAlbumAsc();
    List<AudioFile> sortListByAlbumDesc();
    List<AudioFile> sortListByArtistAsc();
    List<AudioFile> sortListByArtistDesc();
    List<AudioFile> sortListByGenreAsc();
    List<AudioFile> sortListByGenreDesc();
    List<AudioFile> sortListByYearAsc();
    List<AudioFile> sortListByYearDesc();
    List<AudioFile> sortListByDurationAsc();
    List<AudioFile> sortListByDurationDesc();
}