package com.visual_audio.Subsystems.FrequencyInterpreter;

public class Device {
    private String name;
    private String deviceType;
    private SoundProfile soundProfile;
    private String company;
    private float msrp;

    Device(String name, String deviceType, SoundProfile soundProfile, String company, float msrp){
        this.name = name;
        this.deviceType = deviceType;
        this.soundProfile = soundProfile;
        this.company = company;
        this.msrp = msrp;
    }

    public String getName() {
        return name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public SoundProfile getSoundProfile() {
        return soundProfile;
    }

    public String getCompany() {
        return company;
    }

    public float getMsrp() {
        return msrp;
    }

    @Override
    public String toString() {
        return "Device [name=" + name + ", deviceType=" + deviceType + ", soundProfile=" + soundProfile + ", company="
                + company + ", msrp=" + msrp + "]";
    }
    
}
