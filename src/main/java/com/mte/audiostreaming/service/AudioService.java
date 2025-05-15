package com.mte.audiostreaming.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import static com.mte.audiostreaming.specification.AudioFileSpecification.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        boolean asc
    ) {
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
        Integer year
    ) {
        Specification<AudioFile> spec = Specification
            .where(globalTextSearch(term))
            .and(nameContains(name))
            .and(albumContains(album))
            .and(artistContains(artist))
            .and(genreContains(genre))
            .and(yearEquals(year));

        return audioRepository.findAll(spec);
    }

}
