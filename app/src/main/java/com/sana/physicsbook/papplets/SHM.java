package com.sana.physicsbook.papplets;

import processing.core.PApplet;

public class SHM extends PApplet {

    int period;
    int amplitude;

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setup() {

    }

    public void draw() {
        background(255);

//        float period = 120;
//        float amplitude = 300;
        // Calculating horizontal position according to formula for simple harmonic motion
        float x = amplitude * sin(TWO_PI * frameCount / period);
        stroke(0);
        strokeWeight(2);
        fill(127);
        translate(width/2,height/2);
        line(0,0,x,0);
        ellipse(x,0,48,48);
    }
}
