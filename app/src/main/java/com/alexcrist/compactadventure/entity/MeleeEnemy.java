package com.alexcrist.compactadventure.entity;

public abstract class MeleeEnemy extends Enemy {

    public float speed;
    public float turningRadius;

    public MeleeEnemy(float x, float y, float angle, Player player) {
        super(x, y, angle, player);
    }

    @Override
    public void update() {
        super.update();

        float dY = player.y - y;
        float dX = player.x - x;
        float angleToPlayer = (float) Math.toDegrees(Math.atan2(dY, dX));
        angleToPlayer = angleToPlayer < 0 ? angleToPlayer + 360 : angleToPlayer;

        if (Math.abs(angleToPlayer - angle) < turningRadius) {
            angle = angleToPlayer;
        } else {
            float dAngle = angleToPlayer - angle;
            dAngle = dAngle < 0 ? dAngle + 360 : dAngle;

            if (dAngle > 180) {
                angle = angle - turningRadius;
            } else {
                angle = angle + turningRadius;
            }
        }

        fVel = speed;
    }
}
