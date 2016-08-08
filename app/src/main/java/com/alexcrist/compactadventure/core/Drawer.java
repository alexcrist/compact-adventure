package com.alexcrist.compactadventure.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.alexcrist.compactadventure.R;
import com.alexcrist.compactadventure.entity.Entity;

public class Drawer {

    private World world;
    private Bitmap playerImage;
    private Bitmap sword1Image;
    private Bitmap skeletonImage;
    private Bitmap balloonImage;
    private Paint shadowPaint;
    private int w;
    private int h;
    private boolean debugMode;

    private final static float SHADOW_DISTANCE = 1.01f;
    private final static float SHADOW_SIZE = 1.1f;

    public Drawer(World world, Resources res) {
        this.world = world;
        this.shadowPaint = new Paint();
        this.shadowPaint.setARGB(100, 100, 100, 100);
        this.debugMode = false;
        initBitmaps(res);
    }

    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;
        scaleBitmaps(w, h);
    }

    public void draw(Canvas canvas) {
        for (Entity entity : world.entities) {
            if (entity.alive) {

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
                        drawSwordHitbox(canvas, x, y, radius, angle);
                        break;
                }

                drawEntityHitbox(canvas, x, y, radius);
            }
        }
    }

    private void drawBitmap(Canvas canvas, Bitmap image, float x, float y, float angle) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        canvas.drawBitmap(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        canvas.restore();
    }

    private void drawShadow(Canvas canvas, float x, float y, float radius) {
        float dX = x - w / 2;
        float dY = y - h / 2;
        float distFromOrigin = (float) Math.sqrt(dX * dX + dY * dY);
        float angleToOrigin = (float) Math.atan2(dY, dX);

        float shadowX = w / 2 + distFromOrigin * SHADOW_DISTANCE * (float) Math.cos(angleToOrigin);
        float shadowY = h / 2  + distFromOrigin * SHADOW_DISTANCE * (float) Math.sin(angleToOrigin);

        canvas.drawCircle(shadowX, shadowY, radius * SHADOW_SIZE, shadowPaint);
    }

    private void drawEntityHitbox(Canvas canvas, float x, float y, float radius) {
        if (debugMode) {
            Paint paint = new Paint();
            paint.setARGB(200, 0, 0, 255);
            canvas.drawCircle(x, y, radius, paint);
        }
    }

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

    private void initBitmaps(Resources res) {
        playerImage = BitmapFactory.decodeResource(res, R.drawable.player_image);
        sword1Image = BitmapFactory.decodeResource(res, R.drawable.sword_1_image);
        skeletonImage = BitmapFactory.decodeResource(res, R.drawable.skeleton_image);
        balloonImage = BitmapFactory.decodeResource(res, R.drawable.balloon_image);
    }

    private void scaleBitmaps(int w, int h) {
        // TODO - got to do this later
    }


}
