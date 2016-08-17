package com.alexcrist.compactadventure.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.alexcrist.compactadventure.R;
import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;

public class Drawer {

    public int screenFadeProgress;

    private World world;

    private Bitmap playerImage;
    private Bitmap sword1Image;
    private Bitmap skeletonImage;
    private Bitmap balloonImage;

    private Paint shadowPaint;
    private Paint invincibilityPaint;
    private Paint healthPaint;
    private Paint healthBackgroundPaint;
    private Paint enemiesPaint;
    private Paint enemiesBackgroundPaint;
    private Paint successPaint;
    private Paint failurePaint;

    private int w;
    private int h;
    private boolean debugMode;

    public final static int SCREEN_FADE_DURATION = 120;

    private final static float SHADOW_DISTANCE = 1.01f;
    private final static float SHADOW_SIZE = 1.1f;
    private final static int MAX_INVINCIBILITY_ALPHA = 150;
    private final static float STATUS_BAR_HEIGHT = .02f;

    public Drawer(World world, Resources res) {
        this.world = world;

        this.shadowPaint = new Paint();
        this.invincibilityPaint = new Paint();
        this.healthPaint = new Paint();
        this.healthBackgroundPaint = new Paint();
        this.enemiesPaint = new Paint();
        this.enemiesBackgroundPaint = new Paint();
        this.successPaint = new Paint();
        this.failurePaint = new Paint();

        this.shadowPaint.setARGB(100, 100, 100, 100);
        this.invincibilityPaint.setARGB(150, 255, 0, 0);
        this.healthPaint.setARGB(150, 0, 255, 0);
        this.healthBackgroundPaint.setARGB(150, 255, 0, 0);
        this.enemiesPaint.setARGB(150, 100, 0, 255);
        this.enemiesBackgroundPaint.setARGB(150, 0, 0, 0);
        this.successPaint.setARGB(0, 255, 255, 255);
        this.failurePaint.setARGB(0, 0, 0, 0);

        this.screenFadeProgress = 0;

        this.debugMode = false;
        initBitmaps(res);
    }

    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;
        scaleBitmaps(w, h);
    }

    // Handles all drawing, is called repeatedly
    public void draw(Canvas canvas) {
        drawHealthBar(canvas, world.player.health, world.player.maxHealth);
        drawEnemiesBar(canvas, world.numEnemies, world.maxEnemies);

        for (Entity entity : world.entities) {
            if (entity.alive) {
                drawEntity(canvas, entity);
            }
        }

        handleGameOver(canvas);
    }

    // Draw an entity
    private void drawEntity(Canvas canvas, Entity entity) {
        float x = entity.x;
        float y = entity.y;
        float angle = entity.angle;
        float radius = entity.radius;

        drawShadow(canvas, x, y, radius);

        switch (entity.type()) {

            case Entity.SKELETON_TYPE:
                drawBitmap(canvas, skeletonImage, x, y, angle);
                break;

            case Entity.BALLOON_TYPE:
                drawBitmap(canvas, balloonImage, x, y, angle);
                break;

            case Entity.PLAYER_TYPE:
                drawBitmap(canvas, sword1Image, x, y, angle);
                drawBitmap(canvas, playerImage, x, y, angle);
                drawInvincibility(canvas, x, y, radius);
                drawSwordHitbox(canvas, x, y, radius, angle);
                break;
        }

        drawEntityHitbox(canvas, x, y, radius);
    }

    // If the game is over, fade out screen, then present modal
    private void handleGameOver(Canvas canvas) {
        switch (world.gameStatus) {

            case World.GAME_OVER_SUCCESS:
                drawScreenCover(canvas, successPaint);
                break;

            case World.GAME_OVER_FAILURE:
                drawScreenCover(canvas, failurePaint);
                break;
        }
    }

    // Draw a rectangle covering everything onscreen
    private void drawScreenCover(Canvas canvas, Paint paint) {
        if (screenFadeProgress < SCREEN_FADE_DURATION) {
            screenFadeProgress++;
        }

        float ratio = (float) screenFadeProgress / SCREEN_FADE_DURATION;
        paint.setAlpha((int) (255 * ratio));
        canvas.drawRect(0, 0, w, h, paint);
    }

    // Draw player's health bar
    private void drawHealthBar(Canvas canvas, int health, int maxHealth) {
        float midpoint = w * health / maxHealth;

        canvas.drawRect(0, h - h * STATUS_BAR_HEIGHT, midpoint, h, healthPaint);
        canvas.drawRect(midpoint, h - h * STATUS_BAR_HEIGHT, w, h, healthBackgroundPaint);
    }

    // Draw a bar indicating the number of remaining enemies
    private void drawEnemiesBar(Canvas canvas, int numEnemies, int maxEnemies) {
        float midpoint = w * numEnemies / maxEnemies;

        canvas.drawRect(0, 0, midpoint, h * STATUS_BAR_HEIGHT, enemiesPaint);
        canvas.drawRect(midpoint, 0, w, h * STATUS_BAR_HEIGHT, enemiesBackgroundPaint);
    }

    // Draws a bitmap at given coordinates rotated at given angle
    private void drawBitmap(Canvas canvas, Bitmap image, float x, float y, float angle) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        canvas.drawBitmap(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        canvas.restore();
    }

    // Draw a generic circular shadow
    private void drawShadow(Canvas canvas, float x, float y, float radius) {
        float dX = x - w / 2;
        float dY = y - h / 2;
        float distFromOrigin = (float) Math.sqrt(dX * dX + dY * dY);
        float angleToOrigin = (float) Math.atan2(dY, dX);

        float shadowX = w / 2 + distFromOrigin * SHADOW_DISTANCE * (float) Math.cos(angleToOrigin);
        float shadowY = h / 2  + distFromOrigin * SHADOW_DISTANCE * (float) Math.sin(angleToOrigin);

        canvas.drawCircle(shadowX, shadowY, radius * SHADOW_SIZE, shadowPaint);
    }

    // Draw a red circle over the player indicating invincibility
    private void drawInvincibility(Canvas canvas, float x, float y, float radius) {
        if (world.player.isInvincible()) {
            float ratio = (float) world.player.invincibilityTimer / Player.MAX_INVINCIBILITY_TIME;
            invincibilityPaint.setAlpha((int) (MAX_INVINCIBILITY_ALPHA * ratio));
            canvas.drawCircle(x, y, radius, invincibilityPaint);
        }
    }

    // For development: clearly draws an entity's hit-box
    private void drawEntityHitbox(Canvas canvas, float x, float y, float radius) {
        if (debugMode) {
            Paint paint = new Paint();
            paint.setARGB(200, 0, 0, 255);
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    // For development: clearly draws the player's sword's hit-box
    private void drawSwordHitbox(Canvas canvas, float x, float y, float radius, float angle) {
        if (debugMode) {
            Paint paint = new Paint();
            paint.setARGB(200, 0, 255, 0);

            float left = x + radius;
            float top = y - world.player.swordWidth;
            float right = x + radius + world.player.swordLength;
            float bottom = y + world.player.swordWidth;

            canvas.save();
            canvas.rotate(angle, x, y);
            canvas.drawRect(left, top, right, bottom, paint);
            canvas.restore();
        }
    }

    // Initialize all the bitmaps
    private void initBitmaps(Resources res) {
        playerImage = BitmapFactory.decodeResource(res, R.drawable.player_image);
        sword1Image = BitmapFactory.decodeResource(res, R.drawable.sword_1_image);
        skeletonImage = BitmapFactory.decodeResource(res, R.drawable.skeleton_image);
        balloonImage = BitmapFactory.decodeResource(res, R.drawable.balloon_image);
    }

    // Scale all the bitmaps to the phone's screen size
    private void scaleBitmaps(int w, int h) {
        // TODO - got to do this later
    }


}
