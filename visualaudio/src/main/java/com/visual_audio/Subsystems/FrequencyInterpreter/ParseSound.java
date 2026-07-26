package com.visual_audio.Subsystems.FrequencyInterpreter;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;

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
        } 
        catch (Exception e) {
            System.out.println("Could not update file.");
            e.printStackTrace();
        }
    }

    public int getSampleRate(){
        try {
            AudioFile tempFile = AudioFileIO.read(this.audioFile);
            AudioHeader audioHeader = tempFile.getAudioHeader();

            int sampleRate = audioHeader.getSampleRateAsNumber();
            return sampleRate;
        } 
        catch (Exception e) {
            System.out.println("Could not retreive the sample rate of the audio file.");
            e.printStackTrace();
            return 0;
        }
    }

    //method to convert the byte arrays extracted from uploaded audio into  double arrays
    public double[] byteToDouble(byte[] audioData){
        double[] sampledAud = new double[audioData.length/2];
        
        for(int i = 0, p = 0; i < audioData.length -1; i += 2, p++){

            int sampleInt = (audioData[i] << 8) | (audioData[i+1] & 0xff);
            sampledAud[p] = sampleInt / 32768.0;
        }
        return sampledAud;
    }

    public void getFrequencies(double[] audioData){

    }

    public void getAverageLowHz(){

    }
}
