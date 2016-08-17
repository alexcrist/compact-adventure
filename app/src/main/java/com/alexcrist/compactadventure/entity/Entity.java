package com.alexcrist.compactadventure.entity;

public abstract class Entity {

    public float x;      // x position
    public float y;      // y position
    public float angle;  // angle
    public float radius; // hit-box radius

    public boolean alive;

    public static final int PLAYER_TYPE = 0;
    public static final int SKELETON_TYPE = 1;
    public static final int BALLOON_TYPE = 2;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
        this.angle = 0;
        this.alive = true;
    }

    // Update the entity every frame
    public abstract void update();

    // Returns the type of entity
    public abstract int type();

    // Scales coordinates from [0-1] to [0-screenSize]
    public void scaleToScreen(int w, int h) {
        x = x * w;
        y = y * h;
        radius = radius * h;
    }

    public boolean isEnemy() {
        return false;
    }
}
