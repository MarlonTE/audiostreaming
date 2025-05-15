package com.mte.audiostreaming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mte.audiostreaming.service.AudioScannerService;

@SpringBootApplication
public class AudiostreamingApplication implements CommandLineRunner {

    @Autowired
    private AudioScannerService audioScannerService;

    public static void main(String[] args) {
        SpringApplication.run(AudiostreamingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String Folder = "C:\\Users\\marlo\\Music";
        audioScannerService.scanDirectory(Folder);
    }
}
