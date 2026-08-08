package com.visual_audio.Subsystems.Client;

import java.io.File;
import java.util.List;

import com.visual_audio.Subsystems.AudioParsing.MediaFileReader;
import com.visual_audio.Subsystems.FrequencyInterpreter.Device;
import com.visual_audio.Subsystems.FrequencyInterpreter.ParseSound;
 
public class Client {
    private File audioFile;
    private ParseSound parser = new ParseSound(null);

    public boolean uploadFile(String fileName, MediaFileReader mediaReader) {
        File uploadFile = new File(fileName);
        if (uploadFile.equals(null)) {
            return false;
        } 
        else {
            this.audioFile = uploadFile;
        }

        try {
            this.parser.changeFile(uploadFile);
            byte[] audioBytes = mediaReader.getAudio(uploadFile);
            double[] audioConvert = this.parser.byteToDouble(audioBytes);
            this.parser.getFrequencies(audioConvert);

        }  
        catch (Exception e) {
            System.out.println("Could not upload file.");
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyFormat(String fileName) {
        return false;
    }

    public String getFileName( ) {
        return null;
    }

    public String getFileInfo() {
        return null;
    }

    public List<String> getDevices() {
        return null;
    }

    private List<Device> matchDevices() {
        return null;
    }
}
