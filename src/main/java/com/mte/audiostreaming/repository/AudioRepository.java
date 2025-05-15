package com.mte.audiostreaming.repository;

import com.mte.audiostreaming.model.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioRepository extends JpaRepository<AudioFile, Long>, JpaSpecificationExecutor<AudioFile> {
}
