package com.alexcrist.compactadventure.core;

import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;
import com.alexcrist.compactadventure.util.CollisionCalculator;

public class World {

    private CollisionCalculator collisionCalculator;
    private int w;
    private int h;

    public int numEnemies;
    public int maxEnemies;

    public int gameStatus;

    public Player player;
    public Entity[] entities;

    public final static int GAME_IN_PROGRESS = 0;
    public final static int GAME_OVER_SUCCESS = 1;
    public final static int GAME_OVER_FAILURE = 2;

    public World(Player player, Entity[] entities) {
        this.player = player;
        this.entities = entities;
        this.collisionCalculator = new CollisionCalculator();
        this.maxEnemies = countEnemies(entities);
        this.numEnemies = maxEnemies;
        this.gameStatus = GAME_IN_PROGRESS;
    }

    // Update all entities in the world
    public void update() {
        for (Entity entity : entities) {
            if (entity.alive) {
                entity.update();

                if (player.alive) {
                    // Specific behavior for different entity types
                    switch (entity.type()) {

                        case Entity.PLAYER_TYPE:
                            keepInbounds(entity);
                            break;

                        case Entity.SKELETON_TYPE:
                            handleEnemyDeath(entity);
                            handleEnemyPlayerCollision(entity);
                            break;

                        case Entity.BALLOON_TYPE:
                            handleEnemyDeath(entity);
                            break;
                    }
                }
            }
        }

        if (numEnemies == 0) {
            gameStatus = GAME_OVER_SUCCESS;
        } else if (!player.alive) {
            gameStatus = GAME_OVER_FAILURE;
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
    private void handleEnemyPlayerCollision(Entity entity) {
        if (collisionCalculator.entityCollision(player, entity)) {
            player.damaged(entity.x, entity.y);
        }
    }

    // Test for and handle enemy death scenarios
    private void handleEnemyDeath(Entity entity) {
        // Enemy is alive and collides with sword
        if (entity.alive) {
            if (collisionCalculator.swordCollision(player, entity)) {
                entity.alive = false;
                numEnemies--;
            }
        }
    }

    // Count the total number of entities which are enemies
    private int countEnemies(Entity[] entities) {
        int numEnemies = 0;
        for (Entity entity : entities) {
            if (entity.isEnemy()) {
                numEnemies++;
            }
        }
        return numEnemies;
    }
}
