package com.mte.audiostreaming.repository;

import com.mte.audiostreaming.model.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AudioRepository extends JpaRepository<AudioFile, Long>, JpaSpecificationExecutor<AudioFile> {

    Optional<AudioFile> findByAudioPath(String audioPath);

}
