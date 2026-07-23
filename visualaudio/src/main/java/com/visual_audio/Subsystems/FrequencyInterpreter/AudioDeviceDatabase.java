package com.visual_audio.Subsystems.FrequencyInterpreter;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AudioDeviceDatabase {
    private ArrayList<Device> devices;

    AudioDeviceDatabase() {
        // Read from a json file
        ObjectMapper map = new ObjectMapper();
        try {
            File deviceFile = new File("./visualaudio/src/data/devices.json");
            this.devices = map.readValue(deviceFile, new TypeReference<ArrayList<Device>>(){});
        } catch (Exception e) {
            System.out.println("Could not load in device database.");
            e.printStackTrace();
        }
    }

    public ArrayList<Device> getTopSounds(SoundProfile profile) {
        ArrayList<Device> topDevices = new ArrayList<Device>();
        devices.forEach(d ->{
            if(d.getSoundProfile().equals(profile)){
                topDevices.add(d);
            }
        });
        return topDevices;
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
