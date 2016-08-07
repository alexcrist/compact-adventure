package com.alexcrist.compactadventure.entity;

public abstract class MeleeEnemy extends Enemy {

    public float speed;
    public float turningAngle;

    public MeleeEnemy(float x, float y, Player player) {
        super(x, y, player);
    }

    @Override
    public void update() {
        super.update();

        // Calculate angle to player
        float dY = player.y - y;
        float dX = player.x - x;
        float angleToPlayer = (float) Math.toDegrees(Math.atan2(dY, dX));
        angleToPlayer = angleToPlayer < 0 ? angleToPlayer + 360 : angleToPlayer;
        float dAngle = angleToPlayer - angle;
        dAngle = dAngle < 0 ? dAngle + 360 : dAngle;

        // Determine whether to turn CW or CCW
        if (dAngle < turningAngle || 360 - dAngle < turningAngle) {
            angle = angleToPlayer;
        } else if (dAngle < 180) {
            angle += turningAngle;
        } else {
            angle -= turningAngle;
        }

        // Move forwards at constant speed
        fVel = speed;
    }

    @Override
    public void scaleToScreen(int w, int h) {
        super.scaleToScreen(w, h);
        speed = speed * h;
    }
}
