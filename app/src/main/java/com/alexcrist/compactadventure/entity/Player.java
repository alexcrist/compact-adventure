package com.alexcrist.compactadventure.entity;

public class Player extends Movable {

    public float swordLength;
    public float swordWidth;

    private static final float F_VEL_INCREMENT = .01f; // Amount to increment positional vel. upon move
    private static final float A_VEL_INCREMENT = 5;    // Amount to increment angular vel. upon move
    private static final float PLAYER_RADIUS = .09f;

    public Player(float x, float y, float angle) {
        super(x, y, angle);
        this.radius = PLAYER_RADIUS;
        this.swordLength = .09f;
        this.swordWidth = .01f;
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

    @Override
    public int type() {
        return Entity.PLAYER_TYPE;
    }
}
