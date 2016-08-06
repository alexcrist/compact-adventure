package com.alexcrist.compactadventure.entity;

public abstract class Entity {

    public float x;      // x position
    public float y;      // y position
    public float angle;  // angle
    public float radius; // hit-box radius

    public boolean alive;

    public static final int PLAYER_TYPE = 0;
    public static final int SKELETON_TYPE = 1;

    public Entity(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.alive = true;
    }

    // Update the entity every frame
    public abstract void update();

    // Returns the type of entity
    public abstract int type();
}
