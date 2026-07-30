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
//update uml models to reflect new changes lrod
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
        cooleyTukey(complexAudio, inputLen);
        //probanly
        return complexAudio;
    }

    // split & combine the audio input recursively, compute, and then combine ffts
    //even stores real, odd stores imaginary
    public void cooleyTukey(double[] complexAudio, int n){
        if(n==1){
            return;
        }
        int halved = n/2;
        double[] evens = new double[2*halved];
        double[] odds = new double[2*halved];

        for(int i = 0; i < halved; i++){
            evens[2*i] = complexAudio[4*i];
            evens[2*i+1] = complexAudio[4*i+1]; 

            odds[2*i] = complexAudio[4*i+2];
            odds[2*i+1] = complexAudio[4*i+3];
        }
        //self call for evns and odds
        this.cooleyTukey(evens, n);
        this.cooleyTukey(odds, n);

        //separate method for combining ffts
        for(int i = 0; i < halved; i++){
            double angle = -2*Math.PI * i/n;

            double realTwiddles = Math.cos(angle);
            double imagTwiddles = Math.sin(angle);

            double realOdd = odds[2*i] * realTwiddles - odds[2*i+1] * imagTwiddles;
            double imageOdd = odds[2*i] *realTwiddles - odds[2*i+1] * realTwiddles;

            complexAudio[2*i] = evens[2*i] + realOdd;
            complexAudio[2*i+1] = evens[2*i+1] + imageOdd;
            complexAudio[2*i+n] = evens[2*i] - realOdd;
            complexAudio[2*i+n+1] = evens[2*i+1] - imageOdd;

        }
    }
    public void getAverageLowHz(){

    }
}
