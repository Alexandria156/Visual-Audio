package com.visual_audio.Subsystems.Client;

import java.io.File;

import javax.sound.sampled.spi.AudioFileReader;

public class main {
    private AudioFileReader fileData;
    private File audioFile;

    public boolean uploadAudio(String fileName){
        return false; 
    }

    public boolean uploadVideo(String fileName){
        return false;
    }

    public boolean verifyFormat(String fileName){
        return false;
    }

    public String getFileName(){
        return null;
    }

    public String getFileInfo(){
        return null;
    }
}
