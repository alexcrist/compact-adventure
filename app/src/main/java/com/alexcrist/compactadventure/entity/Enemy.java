package com.alexcrist.compactadventure.entity;

public abstract class Enemy extends Movable {

    protected Player player;

    public Enemy(float x, float y, Player player) {
        super(x, y);
        this.player = player;
    }
}
