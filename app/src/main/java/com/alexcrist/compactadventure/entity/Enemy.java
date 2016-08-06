package com.alexcrist.compactadventure.entity;

public abstract class Enemy extends Movable {

    protected Player player;

    public Enemy(float x, float y, float angle, Player player) {
        super(x, y, angle);
        this.player = player;
    }
}
