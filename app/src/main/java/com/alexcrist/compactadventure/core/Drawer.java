package com.alexcrist.compactadventure.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.alexcrist.compactadventure.R;
import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;
import com.alexcrist.compactadventure.util.BitmapManager;
import com.alexcrist.compactadventure.util.PaintManager;

public class Drawer {

    public int screenFadeProgress;

    private World world;
    private PaintManager paint;
    private BitmapManager bitmap;
    private int w;
    private int h;
    private boolean debugMode;
    private float scaledBarWidth;
    private float scaledBarMargin;

    public final static int SCREEN_FADE_DURATION = 120;

    private final static float SHADOW_DISTANCE = 1.01f;
    private final static float SHADOW_SIZE = 1.1f;
    private final static int MAX_INVINCIBILITY_ALPHA = 150;
    private final static float STATUS_BAR_WIDTH = .03f;
    private final static float STATUS_BAR_MARGIN = .01f;
    private final static float NUM_STATUS_BAR_TICKS = 9;

    public Drawer(World world, Resources res) {
        this.world = world;
        this.paint = new PaintManager();
        this.bitmap = new BitmapManager(res);
        this.screenFadeProgress = 0;
        this.debugMode = false;
    }

    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;
        bitmap.scaleToScreen(w, h);
        scaleConstants(w, h);
    }

    // Handles all drawing, is called repeatedly
    public void draw(Canvas canvas) {
        drawHealthBar(canvas, world.player.health, world.player.maxHealth);
        drawEnemiesBar(canvas, world.numEnemies, world.maxEnemies);

        drawHP(canvas);
        drawProgress(canvas);

        for (Entity entity : world.entities) {
            if (entity.alive || entity.type() == Entity.PLAYER_TYPE) {
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
                drawBitmap(canvas, bitmap.skeleton, x, y, angle);
                break;

            case Entity.BALLOON_TYPE:
                drawBitmap(canvas, bitmap.balloon, x, y, angle);
                break;

            case Entity.PLAYER_TYPE:
                drawBitmap(canvas, bitmap.sword1, x, y, angle);
                drawBitmap(canvas, bitmap.player, x, y, angle);
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
                drawScreenCover(canvas, paint.successCover);
                break;

            case World.GAME_OVER_FAILURE:
                drawScreenCover(canvas, paint.failureCover);
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

    // Draw player's healthBar bar
    private void drawHealthBar(Canvas canvas, int health, int maxHealth) {
        float left = scaledBarMargin;
        float top = scaledBarMargin;
        float right = scaledBarMargin + scaledBarWidth;
        float bottom = h - scaledBarMargin;
        float midpoint = (h - scaledBarMargin * 2) * health / maxHealth + scaledBarMargin;

        canvas.drawRect(left, top, right, midpoint, paint.healthBar);
        canvas.drawRect(left, midpoint, right, bottom, paint.healthBarBG);
        canvas.drawRect(left, top, right, bottom, paint.barBorder);
    }

    // Draw a bar indicating the number of remaining enemiesBar
    private void drawEnemiesBar(Canvas canvas, int numEnemies, int maxEnemies) {
        float left = w - (scaledBarMargin + scaledBarWidth);
        float top = scaledBarMargin;
        float right = w - scaledBarMargin;
        float bottom = h - scaledBarMargin;
        float midpoint = (h - scaledBarMargin * 2) * numEnemies / maxEnemies + scaledBarMargin;

        canvas.drawRect(left, midpoint, right, bottom, paint.enemiesBar);
        canvas.drawRect(left, top, right, midpoint, paint.enemiesBarBG);
        canvas.drawRect(left, top, right, bottom, paint.barBorder);
    }

    // Draw the HP label
    private void drawHP(Canvas canvas) {
        float x = scaledBarMargin * 2 + scaledBarWidth + bitmap.hp.getWidth() / 2;
        float y = scaledBarMargin + bitmap.hp.getHeight() / 2;
        drawBitmap(canvas, bitmap.hp, x, y, 270);
    }

    // Draw the "progress" label
    private void drawProgress(Canvas canvas) {
        float x = w - (scaledBarMargin * 2 + scaledBarWidth + bitmap.progress.getWidth() / 2);
        float y = h - (scaledBarMargin + bitmap.hp.getHeight() / 2);
        drawBitmap(canvas, bitmap.progress, x, y, 270);
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

        canvas.drawCircle(shadowX, shadowY, radius * SHADOW_SIZE, paint.shadow);
    }

    // Draw a red circle over the player indicating invincibility
    private void drawInvincibility(Canvas canvas, float x, float y, float radius) {
        if (world.player.isInvincible()) {
            float ratio = (float) world.player.invincibilityTimer / Player.MAX_INVINCIBILITY_TIME;
            paint.invincibility.setAlpha((int) (MAX_INVINCIBILITY_ALPHA * ratio));
            canvas.drawCircle(x, y, radius, paint.invincibility);
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

    // Scale constants to phone's screen size
    private void scaleConstants(int w, int h) {
        scaledBarWidth = STATUS_BAR_WIDTH * w;
        scaledBarMargin = STATUS_BAR_MARGIN * w;
    }
}
