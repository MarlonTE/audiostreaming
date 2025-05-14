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

@Entity // This annotation is used to specify that the class is an entity and is mapped to a database table
@Data // This annotation is used to generate getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor // This annotation is used to generate a no-args constructor
@AllArgsConstructor // This annotation is used to generate a constructor with all fields as parameters
@Builder // This annotation is used to generate a builder pattern for the class
public class AudioFile {
    @Id // This annotation is used to specify the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation is used to specify the generation strategy for the primary key
    private Long id; // Unique identifier for the audio file

    private String name; // Name of the audio file
    private String album; // Album name

    @ElementCollection // This annotation is used to specify that the field is a collection of basic types or embeddable classes
    private List<String> artists; // List of artists

    @ElementCollection
    private List<String> genres; // List of genres

    private int year; // Year of release

    private String coverPath; // Path to the cover image
    private String lyricsPath; // Path to the lyrics file
    private String audioPath; // Path to the audio file

    private int duration; // Duration in seconds
    private int trackNumber; // Track number in the album

    private String language; // Language of the audio file

    private LocalDateTime createdAt; // Date when the audio file was added to the system

}
