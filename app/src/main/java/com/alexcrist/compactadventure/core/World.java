package com.alexcrist.compactadventure.core;

import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;
import com.alexcrist.compactadventure.entity.Skeleton;

public class World {

    private int level;
    private int w;
    private int h;
    private Skeleton skeleton;

    public Player player;
    public Entity[] entities;

    public World(int level) {
        this.level = level;
        this.player = new Player(.5f, .5f, 0);
        this.skeleton = new Skeleton(.25f, .25f, 0, player);
        this.entities = new Entity[2];
        this.entities[0] = player;
        this.entities[1] = skeleton;
    }

    // Update all entities in the world
    public void update() {
        if (player.alive) {
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

    // Test if enemy should be dead
    private void testForDeath(Entity entity) {
        if (collisionWithSword(entity)) {
            entity.alive = false;
        }
    }

    // Test if enemy is colliding with sword
    private boolean collisionWithSword(Entity entity) {
        // Coordinates of sword's center (sword is a rectangle)
        float swordX = player.x + (player.radius + player.swordLength / 2) * (float) Math.cos(Math.toRadians(player.angle));
        float swordY = player.y + (player.radius + player.swordLength / 2) * (float) Math.sin(Math.toRadians(player.angle));

        // Translate entity so that center of sword lies at (0, 0)
        float tX = entity.x - swordX;
        float tY = entity.y - swordY;

        // Rotate entity around origin so that sword lies horizontally flat
        float entityAngle = (float) Math.toDegrees(Math.atan2(tY, tX));
        float distFromOrigin = (float) Math.sqrt(tX * tX + tY * tY);
        float rX = distFromOrigin * (float) Math.cos(Math.toRadians(entityAngle - player.angle));
        float rY = distFromOrigin * (float) Math.sin(Math.toRadians(entityAngle - player.angle));

        // Define sword's rectangle's width and height
        float width = player.swordLength;
        float height = player.swordWidth;

        // Take absolute value
        float aX = Math.abs(rX);
        float aY = Math.abs(rY);

        if (aX > width / 2 + entity.radius) {
            return false;
        }

        if (aY > height / 2 + entity.radius) {
            return false;
        }

        if (aX <= width / 2) {
            return true;
        }

        if (aY <= height / 2) {
            return true;
        }

        // Calculate distance to corner
        float cX = aX - width / 2;
        float cY = aY - height / 2;
        float cD = (float) Math.sqrt(cX * cX + cY * cY);

        return cD <= entity.radius;
    }
}
