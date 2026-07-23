package com.visual_audio.Subsystems.FrequencyInterpreter;

import java.io.File;

public class ParseSound {
    private File audioFile;
    private int commonLowHz;
    private int commonHighHz;
    private int commonMidHz;

    public ParseSound(File audioFile){
        this.audioFile = audioFile;
    }

    public void changeFile(File newFile){
        try {
            this.audioFile = newFile;
        } catch (Exception e) {
            System.out.println("Could not update file.");
            e.printStackTrace();
        }
    }

    public void getAverageLowHz(){
        
    }
}
