package com.visual_audio.Subsystems.AudioParsing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes; 

public class VideoReaderAdapter implements MediaFileReader{

    @Override
    public String getName(File file) {
        if (!fileExists(file)) {
            return "File cannot be read.";
        }
        String name = file.getName();
        return name;
    }

    @Override
    public float getSize(File file) {
        if (!fileExists(file)) {
            return 0f;
        }
        float fileSize = file.getTotalSpace();
        return fileSize;
    }

    @Override
    public String getInfo(File file) {
        String infoMesg = "No information found.";
        if (!fileExists(file)) {
            return infoMesg;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            BodyContentHandler contentHandler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Mp3Parser autoParser = new Mp3Parser();

            autoParser.parse(inputStream, contentHandler, metadata, new ParseContext());
            inputStream.close();

            infoMesg = file.getName() + "\n" + metadata.get("duration");
        } catch (Exception e) {
            System.out.println("Could not retreive file details.");
            e.printStackTrace();
        }
        return infoMesg;
    }

    @Override
    public byte[] getAudio(File file) {
        if (!fileExists(file)) {
            return null;
        }
        
        File convertedFile = this.convertToAudio(file);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(convertedFile);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int readBytes;

            while ((readBytes = audioStream.read(buffer)) != -1) {
                byteOut.write(buffer, 0, readBytes);
            }
            
            return byteOut.toByteArray();
        } catch (Exception e) {
            System.out.println("Could not extract file audio.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean fileExists(File file) {
        return file.canRead();
    }
    
    public File convertToAudio(File file){
        AudioAttributes fileAudio = new AudioAttributes();
        EncodingAttributes encAttributes = new EncodingAttributes();
        Encoder encoder = new Encoder();
        MultimediaObject mmObject = new MultimediaObject(file);

        fileAudio.setCodec("libmp3lame");
        encAttributes.setOutputFormat("mp3");
        encAttributes.setAudioAttributes(fileAudio);

        File convertVdeo =  new File("tempFile.mp3");
        try {
            encoder.encode(mmObject, convertVdeo, encAttributes);
        } 
        catch (IllegalArgumentException | EncoderException e) {
            e.printStackTrace();
        }
        return convertVdeo;
    }
}
