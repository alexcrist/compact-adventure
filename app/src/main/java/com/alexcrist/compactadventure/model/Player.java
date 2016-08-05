package com.alexcrist.compactadventure.model;

import android.graphics.BitmapFactory;
import android.util.Log;

public class Player extends Entity {

    private float fVel; // Forward velocity
    private float aVel; // Angular velocity clockwise

    private final float F_VEL_INCREMENT = .01f; // Amount to increment positional vel. upon move
    private final float A_VEL_INCREMENT = 5;  // Amount to increment angular vel. upon move

    public Player(float x, float y, float angle, float radius) {
        super(x, y, angle, radius);
        this.fVel = 0;
        this.aVel = 0;
    }

    @Override
    public void update() {

        // Move player
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

    public void leftUpAction() {
        fVel += F_VEL_INCREMENT;
        aVel -= A_VEL_INCREMENT;
    }

    public void rightUpAction() {
        fVel += F_VEL_INCREMENT;
        aVel += A_VEL_INCREMENT;
    }

    public void leftDownAction() {
        fVel -= F_VEL_INCREMENT;
        aVel += A_VEL_INCREMENT;
    }

    public void rightDownAction() {
        fVel -= F_VEL_INCREMENT;
        aVel -= A_VEL_INCREMENT;
    }

    public int type() {
        return Entity.PLAYER_TYPE;
    }
}
