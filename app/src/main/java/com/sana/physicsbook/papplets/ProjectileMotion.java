package com.sana.physicsbook.papplets;

import processing.core.PApplet;
import processing.core.PVector;

public class ProjectileMotion extends PApplet {
    float angle = -PI/4;
    PVector position = new PVector(50, 300);
    boolean shot = false;

    CannonBall ball;
    double optgravity;

    public void setOptgravity(double optgravity) {
        this.optgravity = optgravity;
    }

    public double getOptgravity() {
        return optgravity;
    }

    public void setup() {

        ball = new CannonBall(position.x, position.y);
    }

    public void draw() {
        background(255);

        pushMatrix();
        translate(position.x, position.y);
        rotate(angle);
        rect(0, -5, 80, 20);
        popMatrix();

        if (shot) {
            PVector gravity = new PVector(0, (float) optgravity);
            ball.applyForce(gravity);
            ball.update();
        }
        ball.display();

        if (ball.position.y > height) {
            ball = new CannonBall(position.x, position.y);
            shot = false;
        }
    }
    public void Right(){
        angle += 0.1;

    }
    public void Left(){
        angle -= 0.1;

    }

    public void mousePressed() {


            shot = true;
            PVector force = PVector.fromAngle(angle);
            force.mult(10);
            ball.applyForce(force);

    }
    class CannonBall {
        // All of our regular motion stuff
        PVector position;
        PVector velocity;
        PVector acceleration;

        // Size
        float r = 14;
        float topspeed = 10;



        CannonBall(float x, float y) {
            position = new PVector(x,y);
            velocity = new PVector();
            acceleration = new PVector();
        }

        // Standard Euler integration
        void update() {
            velocity.add(acceleration);
            velocity.limit(topspeed);
            position.add(velocity);
            acceleration.mult(0);
        }

        void applyForce(PVector force) {
            acceleration.add(force);
        }


        void display() {
            stroke(0);
            strokeWeight(2);
            pushMatrix();
            translate(position.x,position.y);
            ellipse(0,0,r*2,r*2);
            popMatrix();
        }
    }
}
