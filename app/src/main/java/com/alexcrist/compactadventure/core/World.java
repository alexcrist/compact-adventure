package com.alexcrist.compactadventure.core;

import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;
import com.alexcrist.compactadventure.entity.Skeleton;

public class World {

    private int level;
    private Player player;
    private Skeleton skeleton;
    public Entity[] entities;


    public World(int level) {
        this.level = level;
        this.player = new Player(0, 0, 0);
        this.skeleton = new Skeleton(0, 0, 0, player);
        this.entities = new Entity[2];
        this.entities[0] = player;
        this.entities[1] = skeleton;
    }

    // Update all entities in the world
    public void update() {
        for (Entity entity : entities) {
            if (entity.alive) {
                entity.update();

                // Behavior for player
                if (entity.type() == Entity.PLAYER_TYPE) {
                    keepInbounds(entity);
                }

                // Behavior for skeleton
                if (entity.type() == Entity.SKELETON_TYPE) {
                    testForDeath(entity);
                }
            }
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
        if (entity.x < -1) {
            entity.x = -1;
        } else if (entity.x > 1) {
            entity.x = 1;
        }

        if (entity.y < -1) {
            entity.y = -1;
        } else if (entity.y > 1) {
            entity.y = 1;
        }
    }

    // Test if enemy should be dead
    private void testForDeath(Entity entity) {
        if (collisionWithSword(entity)) {
            entity.alive = false;
        }
    }

    // Test if enemy is colliding with sword
    private boolean collisionWithSword(Entity entity) {
        return false;
    }
}
