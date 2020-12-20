package com.sana.physicsbook.papplets;

import processing.core.PApplet;
import processing.core.PVector;

public class DragForce extends PApplet {
    Mover[] movers = new Mover[12];

    // Liquid
    Liquid liquid;

    public void setup() {

        reset();
        // Create liquid object
        liquid = new Liquid(0, height/2, width, height/2, (float) 0.1);
    }

    public void draw() {
        background(255);

        // Draw water
        liquid.display();

        for (int i = 0; i < movers.length; i++) {

            // Is the Mover in the liquid?
            if (liquid.contains(movers[i])) {
                // Calculate drag force
                PVector dragForce = liquid.drag(movers[i]);
                // Apply drag force to Mover
                movers[i].applyForce(dragForce);
            }

            // Gravity is scaled by mass here!
            PVector gravity = new PVector(0, (float) (0.1*movers[i].mass));
            // Apply gravity
            movers[i].applyForce(gravity);

            // Update and display
            movers[i].update();
            movers[i].display();
            movers[i].checkEdges();
        }

        fill(0);
        //text("click mouse to reset", 10, 30);
    }

    public void mousePressed() {
        reset();
    }

    // Restart all the Mover objects randomly
    void reset() {
        for (int i = 0; i < movers.length; i++) {
            movers[i] = new Mover(random((float) 0.5, 3), 40+i*90, 0);
        }
    }
    class Liquid {


        // Liquid is a rectangle
        float x, y, w, h;
        // Coefficient of drag
        float c;

        Liquid(float x_, float y_, float w_, float h_, float c_) {
            x = x_;
            y = y_;
            w = w_;
            h = h_;
            c = c_;
        }

        // Is the Mover in the Liquid?
        boolean contains(Mover m) {
            PVector l = m.position;
            return l.x > x && l.x < x + w && l.y > y && l.y < y + h;
        }

        // Calculate drag force
        PVector drag(Mover m) {
            // Magnitude is coefficient * speed squared
            float speed = m.velocity.mag();
            float dragMagnitude = c * speed * speed;

            // Direction is inverse of velocity
            PVector dragForce = m.velocity.get();
            dragForce.mult(-1);

            // Scale according to magnitude
            // dragForce.setMag(dragMagnitude);
            dragForce.normalize();
            dragForce.mult(dragMagnitude);
            return dragForce;
        }

        void display() {
            noStroke();
            fill(50);
            rect(x, y, w, h);
        }
    }
    class Mover {

        // position, velocity, and acceleration
        PVector position;
        PVector velocity;
        PVector acceleration;

        // Mass is tied to size
        float mass;

        Mover(float m, float x, float y) {
            mass = m;
            position = new PVector(x, y);
            velocity = new PVector(0, 0);
            acceleration = new PVector(0, 0);
        }

        // Newton's 2nd law: F = M * A
        // or A = F / M
        void applyForce(PVector force) {
            // Divide by mass
            PVector f = PVector.div(force, mass);
            // Accumulate all forces in acceleration
            acceleration.add(f);
        }

        void update() {

            // Velocity changes according to acceleration
            velocity.add(acceleration);
            // position changes by velocity
            position.add(velocity);
            // We must clear acceleration each frame
            acceleration.mult(0);
        }

        // Draw Mover
        void display() {
            stroke(0);
            strokeWeight(2);
            fill(127, 200);
            ellipse(position.x, position.y, mass*16, mass*16);
        }

        // Bounce off bottom of window
        void checkEdges() {
            if (position.y > height) {
                velocity.y *= -0.9;  // A little dampening when hitting the bottom
                position.y = height;
            }
        }
    }
}
