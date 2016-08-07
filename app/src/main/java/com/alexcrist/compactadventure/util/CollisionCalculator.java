package com.alexcrist.compactadventure.util;

import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;

public class CollisionCalculator {

    public boolean entityCollision(Entity e1, Entity e2) {
        float dX = e1.x - e2.x;
        float dY = e1.y - e2.y;
        float distSquared = dX * dX + dY * dY;
        float radiiSum = e1.radius + e2.radius;

        return (distSquared < radiiSum * radiiSum) && e1.alive && e2.alive;
    }

    public boolean swordCollision(Player player, Entity entity) {
        // Coordinates of sword's center (sword is a rectangle)
        float swordX = player.x + (player.radius + player.swordLength / 2) * (float) Math.cos(Math.toRadians(player.angle));
        float swordY = player.y + (player.radius + player.swordLength / 2) * (float) Math.sin(Math.toRadians(player.angle));

        // Translate entity so that center of sword lies at (0, 0)
        float tX = entity.x - swordX;
        float tY = entity.y - swordY;

        // Rotate entity around origin so that sword lies horizontally flat
        float entityAngle = (float) Math.atan2(tY, tX);
        float distFromOrigin = (float) Math.sqrt(tX * tX + tY * tY);
        float rX = distFromOrigin * (float) Math.cos(entityAngle - Math.toRadians(player.angle));
        float rY = distFromOrigin * (float) Math.sin(entityAngle - Math.toRadians(player.angle));

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
        float cDSquared = cX * cX + cY * cY;

        return cDSquared <= entity.radius * entity.radius;
    }

}
