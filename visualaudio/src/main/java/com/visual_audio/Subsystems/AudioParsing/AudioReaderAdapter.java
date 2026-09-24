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

public class AudioReaderAdapter implements MediaFileReader {

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

            infoMesg = metadata.get("title") + "\n" + metadata.get("artist") + "\n" + metadata.get("album") + "\n"
                    + metadata.get("duration");

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
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
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

}
