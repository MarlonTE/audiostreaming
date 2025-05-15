package com.mte.audiostreaming.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String album;
    @ElementCollection
    private List<String> artists;
    @ElementCollection
    private List<String> genres;
    private int year;
    private String coverPath;
    private String lyricsPath;
    private String audioPath;
    private long duration;
    private long size;
    private int trackNumber;
    private String language;
    private LocalDateTime createdAt;
}
