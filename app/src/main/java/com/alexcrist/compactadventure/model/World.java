package com.alexcrist.compactadventure.model;

import android.view.MotionEvent;

public class World {

    private int level;
    private Player player;
    public Entity[] entities;

    public World(int level) {
        this.level = level;
        this.player = new Player(0, 0, 0, .03f);
        this.entities = new Entity[1];
        this.entities[0] = player;
    }

    // Update all entities in the world
    public void update() {
        for (Entity entity : entities) {
            entity.update();

            if (entity.type() == Entity.PLAYER_TYPE) {
                keepInbounds(entity);
            }
        }
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
}
