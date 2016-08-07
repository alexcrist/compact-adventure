package com.alexcrist.compactadventure.core;

import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;
import com.alexcrist.compactadventure.util.CollisionCalculator;

public class World {

    private CollisionCalculator collisionCalculator;
    private int w;
    private int h;

    public Player player;
    public Entity[] entities;


    public World(Player player, Entity[] entities) {
        this.player = player;
        this.entities = entities;
        this.collisionCalculator = new CollisionCalculator();
    }

    // Update all entities in the world
    public void update() {
        if (player.alive) {
            for (Entity entity : entities) {
                if (entity.alive) {
                    entity.update();

                    // Specific behavior for different entity types
                    switch (entity.type()) {

                        case Entity.PLAYER_TYPE:
                            keepInbounds(entity);
                            break;

                        case Entity.SKELETON_TYPE:
                            handleEnemyDeath(entity);
                            handlePlayerCollision(entity);
                            break;

                        case Entity.BALLOON_TYPE:
                            handleEnemyDeath(entity);
                            break;
                    }
                }
            }
        }
    }

    // Scale world to screen size
    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;

        for (Entity entity : entities) {
            entity.scaleToScreen(w, h);
        }
    }

    public void leftUpAction() {
        player.leftUpAction();
    }

    public void rightUpAction() {
        player.rightUpAction();
    }

    public void leftDownAction() {
        player.leftDownAction();
    }

    public void rightDownAction() {
        player.rightDownAction();
    }

    // Keep entity within the screen
    private void keepInbounds(Entity entity) {
        if (entity.x < 0) {
            entity.x = 0;
        } else if (entity.x > w) {
            entity.x = w;
        }

        if (entity.y < 0) {
            entity.y = 0;
        } else if (entity.y > h) {
            entity.y = h;
        }
    }

    // Test for and handle enemy collisions with player
    private void handlePlayerCollision(Entity entity) {
        if (collisionCalculator.entityCollision(player, entity)) {
            playerHit();
        }
    }

    // Test for and handle enemy death scenarios
    private void handleEnemyDeath(Entity entity) {
        // Enemy colliding with sword
        if (collisionCalculator.swordCollision(player, entity)) {
            entity.alive = false;
        }
    }

    // Player has been hit
    private void playerHit() {

    }
}
