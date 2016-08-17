package com.alexcrist.compactadventure.entity;

public class Balloon extends Enemy {

    private final static float BALLOON_RADIUS = .07f;

    public Balloon(float x, float y) {
        super(x, y, null);
        this.radius = BALLOON_RADIUS;
        this.angle = 270;
    }

    @Override
    public void update() {}

    @Override
    public int type() {
        return Entity.BALLOON_TYPE;
    }
}
