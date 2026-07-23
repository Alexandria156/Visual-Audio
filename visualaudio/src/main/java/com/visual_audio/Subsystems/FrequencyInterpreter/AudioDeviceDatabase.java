package com.visual_audio.Subsystems.FrequencyInterpreter;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AudioDeviceDatabase {
    private Device[] devices;

    AudioDeviceDatabase() {
        // Read from a json file
        ObjectMapper map = new ObjectMapper();
        try {
            File deviceFile = new File("./visualaudio/src/data/devices.json");
            this.devices = map.readValue(deviceFile, Device[].class);
        } catch (Exception e) {
            System.out.println("Could not load in device database.");
            e.printStackTrace();
        }
    }

    public Device[] getTopSounds(SoundProfile profile) {
        Device[] filterDevices = {};

        return filterDevices;
    }

    public Device[] getLowPriceSounds(SoundProfile profile) {
        Device[] filterDevices = {};

        return filterDevices;
    }

    public Device[] getHighPriceSounds(SoundProfile profile) {
        Device[] filterDevices = {};

        return filterDevices;
    }

    public Device[] getCompanySounds(SoundProfile profile, String name) {
        Device[] filterDevices = {};

        return filterDevices;
    }
}
