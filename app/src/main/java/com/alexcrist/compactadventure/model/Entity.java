package com.alexcrist.compactadventure.model;

public abstract class Entity {

    public float x;      // x position
    public float y;      // y position
    public float angle;  // angle
    public float radius; // hit-box radius

    public static final int PLAYER_TYPE = 0;

    public Entity(float x, float y, float angle, float radius) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.radius = radius;
    }

    public abstract void update();
    public abstract int type();

}
