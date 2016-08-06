package com.alexcrist.compactadventure.entity;

public abstract class Movable extends Entity {

    protected float fVel; // Forward velocity
    protected float aVel; // Angular velocity clockwise

    public Movable(float x, float y, float angle) {
        super(x, y, angle);
        this.fVel = 0;
        this.aVel = 0;
    }

    @Override
    public void update() {
        // Move the "movable"
        angle += aVel;
        angle = angle % 360;
        x += fVel * Math.cos(Math.toRadians(angle));
        y += fVel * Math.sin(Math.toRadians(angle));

        // Apply friction
        fVel = fVel * .95f;
        aVel = aVel * .95f;

        // Stop velocities if very small
        fVel = Math.abs(fVel) < .0001 ? 0 : fVel;
        aVel = Math.abs(aVel) < .0001 ? 0 : aVel;
    }
}
