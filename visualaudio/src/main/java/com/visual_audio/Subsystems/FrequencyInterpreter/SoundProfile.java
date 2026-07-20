package com.visual_audio.Subsystems.FrequencyInterpreter;

import java.util.ArrayList;

public enum SoundProfile {
    FLAT(20.0, 20000.0),
    BRIGHT(2000.0, 20000.0),
    BALANCED(20.0, 20000.0),
    VSHAPED(new double[] { 20.0, 300.0 }, new double[] { 2000.0, 20000.0 }),
    BASSY(20.0, 2000.0),
    DARK(20.0, 200.0),
    ANALYTICAL(new double[] { 50.0, 500.0 }, new double[] { 1000.0, 20000.0 }),
    WARMSMOOTH(20.0, 1000.0);

    private double min;
    private double max;
    private double[] firstMinMax;
    private double[] secondMinMax;

    SoundProfile(double min, double max) {
        this.min = min;
        this.max = max;
    }

    SoundProfile(double[] firstMinMax, double[] secondMinMax) {
        this.firstMinMax = firstMinMax;
        this.secondMinMax = secondMinMax;
    }

    public double[] getHzRange() {
        if (this.max == 0 && this.min == 0) {
            System.out.println("This sound signature is of multiple frequency ranges.");
            return null;
        }
        double[] hzRange = { min, max };
        
        return hzRange;
    }

    public ArrayList<double[]> getHzRangeMultiple() {
        if (this.firstMinMax == null && this.secondMinMax == null) {
            System.out.println("This sound signature is of one range.");
            return null;
        }
        ArrayList<double[]> hzRanges = new ArrayList<double[]>();
        hzRanges.add(firstMinMax);
        hzRanges.add(secondMinMax);

        return hzRanges;
    }
}
