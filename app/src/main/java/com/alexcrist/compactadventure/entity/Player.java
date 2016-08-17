package com.alexcrist.compactadventure.entity;

public class Player extends Movable {

    public float swordLength;
    public float swordWidth;

    public int invincibilityTimer;
    public int health;
    public int maxHealth;

    private float xVel;
    private float yVel;
    private float speed;
    private float turningAngle;

    public final static int MAX_INVINCIBILITY_TIME = 50;

    private final static int BASE_MAX_HEALTH = 4;
    private final static float SWORD_LENGTH = .11f;
    private final static float SWORD_WIDTH = .011f;
    private final static float PLAYER_RADIUS = .08f;
    private final static float PLAYER_SPEED = .01f;
    private final static float PLAYER_TURNING_ANGLE = 5;
    private final static float POSITIONAL_FRICTION = .8f;
    private final static float KNOCK_BACK_VELOCITY = 20;

    public Player(float x, float y) {
        super(x, y);
        this.radius = PLAYER_RADIUS;
        this.xVel = 0;
        this.yVel = 0;
        this.speed = PLAYER_SPEED;
        this.turningAngle = PLAYER_TURNING_ANGLE;
        this.swordLength = SWORD_LENGTH;
        this.swordWidth = SWORD_WIDTH;
        this.invincibilityTimer = MAX_INVINCIBILITY_TIME;
        this.health = BASE_MAX_HEALTH;
        this.maxHealth = BASE_MAX_HEALTH;
    }

    @Override
    public void update() {
        super.update();

        // Decrement invincibility timer
        invincibilityTimer = invincibilityTimer == 0 ? 0 : invincibilityTimer - 1;

        // Move the player
        x += xVel;
        y += yVel;

        // Apply friction
        xVel = xVel * POSITIONAL_FRICTION;
        yVel = yVel * POSITIONAL_FRICTION;

        // Stop velocities if very small
        xVel = Math.abs(xVel) < .0001 ? 0 : xVel;
        yVel = Math.abs(yVel) < .0001 ? 0 : yVel;
    }

    @Override
    public int type() {
        return Entity.PLAYER_TYPE;
    }

    @Override
    public void scaleToScreen(int w, int h) {
        super.scaleToScreen(w, h);
        speed = speed * h;
        swordLength = swordLength * h;
        swordWidth = swordWidth * h;
    }

    // Player is damaged from an enemy at the given position
    public void damaged(float x, float y) {
        if (!isInvincible()) {
            health--;

            if (health == 0) {
                alive = false;
            }

            invincibilityTimer = MAX_INVINCIBILITY_TIME;
            float dX = this.x - x;
            float dY = this.y - y;
            float angle = (float) Math.atan2(dY, dX);

            xVel = KNOCK_BACK_VELOCITY * (float) Math.cos(angle);
            yVel = KNOCK_BACK_VELOCITY * (float) Math.sin(angle);
        }
    }

    public boolean isInvincible() {
        return invincibilityTimer > 0;
    }

    public void leftUpAction() {
        fVel += speed;
        aVel += turningAngle;
    }

    public void rightUpAction() {
        fVel += speed;
        aVel -= turningAngle;
    }

    public void leftDownAction() {
        fVel -= speed;
        aVel -= turningAngle;
    }

    public void rightDownAction() {
        fVel -= speed;
        aVel += turningAngle;
    }
}
