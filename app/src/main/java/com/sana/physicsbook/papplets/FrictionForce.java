package com.sana.physicsbook.papplets;
import processing.core.PApplet;
import processing.core.PVector;

public class FrictionForce extends PApplet {
    Mover[] movers = new Mover[5];
    int c;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setup() {

        randomSeed(1);
        for (int i = 0; i < movers.length; i++) {
            movers[i] = new Mover(random(1, 4), random(width), 0);
        }
    }

    public void draw() {
        background(255);

        for (int i = 0; i < movers.length; i++) {

            PVector wind = new PVector((float)0.01, 0);
            PVector gravity = new PVector(0, (float) (0.1*movers[i].mass));

            //float c = (float) 0.05;

            PVector friction = movers[i].velocity.get();
            friction.mult(-1);
            friction.normalize();
            friction.mult(c);

            movers[i].applyForce(friction);
            movers[i].applyForce(wind);
            movers[i].applyForce(gravity);

            movers[i].update();
            movers[i].display();
            movers[i].checkEdges();
        }
    }

    public void reset() {
        randomSeed(1);
        for (int i = 0; i < movers.length; i++) {
            movers[i] = new Mover(random(1, 4), random(width), 0);
        }
    }

    class Mover {

        PVector position;
        PVector velocity;
        PVector acceleration;
        float mass;

        Mover(float m, float x , float y) {
            mass = m;
            position = new PVector(x,y);
            velocity = new PVector(0,0);
            acceleration = new PVector(0,0);
        }

        void applyForce(PVector force) {
            PVector f = PVector.div(force,mass);
            acceleration.add(f);
        }

        void update() {
            velocity.add(acceleration);
            position.add(velocity);
            acceleration.mult(0);
        }

        void display() {
            stroke(0);
            strokeWeight(2);
            fill(0,127);
            ellipse(position.x,position.y,mass*16,mass*16);
        }

        void checkEdges() {

            if (position.x > width) {
                position.x = width;
                velocity.x *= -1;
            } else if (position.x < 0) {
                position.x = 0;
                velocity.x *= -1;
            }

            if (position.y > height) {
                velocity.y *= -1;
                position.y = height;
            }

        }

    }
}


