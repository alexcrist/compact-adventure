package com.alexcrist.compactadventure.entity;

public class Player extends Movable {

    public float swordLength;
    public float swordWidth;

    private float speed;
    private float turningAngle;

    private final static float PLAYER_RADIUS = .08f;
    private final static float PLAYER_SPEED = .01f;
    private final static float PLAYER_TURNING_ANGLE = 5;
    private final static float SWORD_LENGTH = .11f;
    private final static float SWORD_WIDTH = .011f;

    public Player(float x, float y, float angle) {
        super(x, y, angle);
        this.radius = PLAYER_RADIUS;
        this.speed = PLAYER_SPEED;
        this.turningAngle = PLAYER_TURNING_ANGLE;
        this.swordLength = SWORD_LENGTH;
        this.swordWidth = SWORD_WIDTH;
    }

    public void leftUpAction() {
        fVel += speed;
        aVel += turningAngle;
    }

    public void rightUpAction() {
        fVel += speed;
        aVel -= turningAngle;
    }

    public void leftDownAction() {
        fVel -= speed;
        aVel -= turningAngle;
    }

    public void rightDownAction() {
        fVel -= speed;
        aVel += turningAngle;
    }

    @Override
    public int type() {
        return Entity.PLAYER_TYPE;
    }

    @Override
    public void scaleToScreen(int w, int h) {
        super.scaleToScreen(w, h);
        speed = speed * h;
        swordLength = swordLength * h;
        swordWidth = swordWidth * h;
    }
}
