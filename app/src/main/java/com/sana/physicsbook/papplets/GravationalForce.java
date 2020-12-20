package com.sana.physicsbook.papplets;

import processing.core.PApplet;
import processing.core.PVector;

public class GravationalForce extends PApplet {
    Mover[] movers = new Mover[10];

    int mass;    // Mass, tied to size

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    Attractor a;

    public void setup() {

        for (int i = 0; i < movers.length; i++) {
            movers[i] = new Mover(random((float) 0.1, 2), random(width), random(height));
        }
        a = new Attractor();
    }

    public void draw() {
        background(255);

        a.display();
        a.drag();
        a.hover(mouseX, mouseY);

        for (int i = 0; i < movers.length; i++) {
            PVector force = a.attract(movers[i]);
            movers[i].applyForce(force);

            movers[i].update();
            movers[i].display();
        }
    }

    public void mousePressed() {
        a.clicked(mouseX, mouseY);
    }

    public void mouseReleased() {
        a.stopDragging();
    }


    class Attractor {


        float G;       // Gravitational Constant
        PVector position;   // position
        boolean dragging = false; // Is the object being dragged?
        boolean rollover = false; // Is the mouse over the ellipse?
        PVector dragOffset;  // holds the offset for when object is clicked on

        Attractor() {
            position = new PVector(width/2,height/2);
            //mass = 20;
            G = 1;
            dragOffset = new PVector((float)0.0,(float)0.0);
        }

        PVector attract(Mover m) {
            PVector force = PVector.sub(position,m.position);   // Calculate direction of force
            float d = force.mag();                              // Distance between objects
            d = constrain(d,(float)5.0,(float)25.0);                        // Limiting the distance to eliminate "extreme" results for very close or very far objects
            force.normalize();                                  // Normalize vector (distance doesn't matter here, we just want this vector for direction)
            float strength = (G * mass * m.mass) / (d * d);      // Calculate gravitional force magnitude
            force.mult(strength);                                  // Get force vector --> magnitude * direction
            return force;
        }

        // Method to display
        void display() {
            ellipseMode(CENTER);
            strokeWeight(4);
            stroke(0);
            if (dragging) fill (50);
            else if (rollover) fill(100);
            else fill(175,200);
            ellipse(position.x,position.y,mass*2,mass*2);
        }

        // The methods below are for mouse interaction
        void clicked(int mx, int my) {
            float d = dist(mx,my,position.x,position.y);
            if (d < mass) {
                dragging = true;
                dragOffset.x = position.x-mx;
                dragOffset.y = position.y-my;
            }
        }

        void hover(int mx, int my) {
            float d = dist(mx,my,position.x,position.y);
            if (d < mass) {
                rollover = true;
            }
            else {
                rollover = false;
            }
        }

        void stopDragging() {
            dragging = false;
        }



        void drag() {
            if (dragging) {
                position.x = mouseX + dragOffset.x;
                position.y = mouseY + dragOffset.y;
            }
        }

    }
    class Mover {

        PVector position;
        PVector velocity;
        PVector acceleration;
        float mass;

        Mover(float m, float x, float y) {
            mass = m;
            position = new PVector(x, y);
            velocity = new PVector(1, 0);
            acceleration = new PVector(0, 0);
        }

        void applyForce(PVector force) {
            PVector f = PVector.div(force, mass);
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
            fill(0,100);
            ellipse(position.x, position.y, mass*25, mass*25);
        }
    }



}
