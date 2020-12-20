package com.sana.physicsbook.papplets;

import processing.core.PApplet;
import processing.core.PVector;

public class SimplePendulum extends PApplet {
    Pendulum p;
    float gravityvalue1;
    int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public float getGravityvalue1() {
        return gravityvalue1;
    }

    public void setGravityvalue1(float gravityvalue1) {
        this.gravityvalue1 = gravityvalue1;
    }

    public void setup() {

        // Make a new Pendulum with an origin position and armlength
        p = new Pendulum(new PVector(width/2,0),length);

    }

    public void draw() {

        background(255);
        p.go();
    }

    public void mousePressed() {
        p.clicked(mouseX,mouseY);
    }

    public void mouseReleased() {
        p.stopDragging();
    }



    class Pendulum {

        PVector position;    // position of pendulum ball
        PVector origin;      // position of arm origin
        float r;             // Length of arm
        float angle;         // Pendulum arm angle
        float aVelocity;     // Angle velocity
        float aAcceleration; // Angle acceleration

        float ballr;         // Ball radius
        float damping;       // Arbitary damping amount

        boolean dragging = false;

        // This constructor could be improved to allow a greater variety of pendulums
        Pendulum(PVector origin_, int r_) {
            // Fill all variables
            origin = origin_.get();
            position = new PVector();
            r = r_;
            angle = PI/4;

            aVelocity = (float) 0.0;
            aAcceleration = (float) 0.0;
            damping = (float) 0.995;   // Arbitrary damping
            ballr = (float) 48.0;      // Arbitrary ball radius
        }

        void go() {
            update();
            drag();    //for user interaction
            display();
        }

        // Function to update position
        void update() {
            // As long as we aren't dragging the pendulum, let it swing!
            if (!dragging) {
//                 gravityvalue1 = (float) 0.4;                              // Arbitrary constant
                aAcceleration = (-1 * gravityvalue1 / length) * sin(angle);
                aVelocity += aAcceleration;                 // Increment velocity
                aVelocity *= damping;                       // Arbitrary damping
                angle += aVelocity;                         // Increment angle
            }
        }

        void display() {
            position.set(length*sin(angle), length*cos(angle), 0);         // Polar to cartesian conversion
            position.add(origin);                              // Make sure the position is relative to the pendulum's origin

            stroke(0);
            strokeWeight(2);
            // Draw the arm
            line(origin.x, origin.y, position.x, position.y);
            ellipseMode(CENTER);
            fill(175);
            if (dragging) fill(0);
            // Draw the ball
            ellipse(position.x, position.y, ballr, ballr);
        }


        // The methods below are for mouse interaction

        // This checks to see if we clicked on the pendulum ball
        void clicked(int mx, int my) {
            float d = dist(mx, my, position.x, position.y);
            if (d < ballr) {
                dragging = true;
            }
        }


        void stopDragging() {
            if (dragging) {
                aVelocity = 0; // No velocity once you let go
                dragging = false;
            }
        }

        void drag() {

            // If we are dragging the ball, we calculate the angle between the
            // pendulum origin and mouse position
            // we assign that angle to the pendulum
            if (dragging) {
                PVector diff = PVector.sub(origin, new PVector(mouseX, mouseY));      // Difference between 2 points
                angle = atan2(-1*diff.y, diff.x) - radians(90);                      // Angle relative to vertical axis
            }
        }
    }


}
