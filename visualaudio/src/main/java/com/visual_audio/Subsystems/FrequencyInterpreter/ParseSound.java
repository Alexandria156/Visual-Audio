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
    //Use the Fast Fourier Transform algorithim to get the frequencies of the audio data to parse hz from low to high
    //Break up method into smaller separate ones, too bulky
    public void getFrequencies(double[] audioData){
        int fourierSize = bitShift(audioData);
        if(fourierSize < 2){
            throw new IllegalArgumentException("Audio data is too short for FFT.");
        }
        
        double[] fourierInput = hannWindow(audioData, fourierSize);

        // separate fft method
        double[] fourierResult = fourierTransform(fourierInput);
    }

    public int bitShift(double[] audioData){
        int fourierSize = 1;
        while(fourierSize <= audioData.length && fourierSize <= 2048){
            fourierSize <<= 1;
        }
        return fourierSize >>=1; 
    }

    public double[] hannWindow(double[] audioData, int fourierSize){
        double[] fourierInput = new double[fourierSize];
        for(int i = 0; i < fourierSize; i++){
            fourierInput[i] = audioData[i] * (0.5 - (0.5 * Math.cos((2*Math.PI*i)/(fourierSize-1))));
        }
        return fourierInput;
    }

    public double[] fourierTransform(double[] audioInput){
        int inputLen = audioInput.length;
        double[] complexAudio = new double[2*inputLen];

        for(int i = 0; i < inputLen; i++){
            //real & imaginary
            complexAudio[2*i] = audioInput[i];
            complexAudio[2*i+1] = 0;
        }

        //call emthod for cooley tukey algorithim 
        return complexAudio;
    }

    public void getAverageLowHz(){

    }
}
