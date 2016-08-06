package com.alexcrist.compactadventure.entity;

public class Skeleton extends MeleeEnemy {

    private static final float SKELETON_RADIUS = .05f;
    private static final float SKELETON_SPEED = .002f;
    private static final float SKELETON_TURNING_RADIUS = .5f;

    public Skeleton(float x, float y, float angle, Player player) {
        super(x, y, angle, player);
        this.radius = SKELETON_RADIUS;
        this.speed = SKELETON_SPEED;
        this.turningRadius = SKELETON_TURNING_RADIUS;
    }

    @Override
    public int type() {
        return Entity.SKELETON_TYPE;
    }
}
