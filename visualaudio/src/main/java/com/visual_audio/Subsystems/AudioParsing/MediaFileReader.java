package com.visual_audio.Subsystems.AudioParsing;

public interface MediaFileReader {

    public String getName();
    public float getSize();
    public String getInfo();
    public byte[] getAudio();
}
