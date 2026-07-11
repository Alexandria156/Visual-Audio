package com.visual_audio.Subsystems.AudioParsing;

import java.io.File;

public interface MediaFileReader {

    public String getName(File file);
    public float getSize(File file);
    public String getInfo(File file);
    public byte[] getAudio(File file);
    public boolean fileExists(File file);
}
