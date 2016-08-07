package com.alexcrist.compactadventure.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.alexcrist.compactadventure.R;
import com.alexcrist.compactadventure.entity.Entity;

public class Drawer {

    private World world;
    private int w;
    private int h;
    private Bitmap playerImage;
    private Bitmap sword1Image;
    private Bitmap skeletonImage;

    public Drawer(World world, Resources res) {
        this.world = world;
        initBitmaps(res);
    }

    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint(); // Placeholder for bitmaps

        for (Entity entity : world.entities) {
            if (entity.alive) {

                float x = entity.x;
                float y = entity.y;
                float angle = entity.angle;
                float radius = entity.radius;

                switch (entity.type()) {

                    case Entity.SKELETON_TYPE:
                        drawBitmap(canvas, skeletonImage, x, y, angle);
                        break;

                    case Entity.PLAYER_TYPE:
                        drawBitmap(canvas, sword1Image, x, y, angle);
                        drawBitmap(canvas, playerImage, x, y, angle);

                        canvas.save();

                        float left = x + radius;
                        float top = y - world.player.swordWidth;
                        float right = x + radius + world.player.swordLength;
                        float bottom = y + world.player.swordWidth;

                        canvas.rotate(angle, x, y);
                        paint.setColor(Color.GREEN);
                        canvas.drawRect(left, top, right, bottom, paint);
                        canvas.restore();

                        break;
                }

                paint.setColor(Color.RED);
                canvas.drawCircle(x, y, radius, paint);
            }
        }
    }

    private void drawBitmap(Canvas canvas, Bitmap image, float x, float y, float angle) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        canvas.drawBitmap(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        canvas.restore();
    }

    private void initBitmaps(Resources res) {
        playerImage = BitmapFactory.decodeResource(res, R.drawable.player_image);
        sword1Image = BitmapFactory.decodeResource(res, R.drawable.sword_1_image);
        skeletonImage = BitmapFactory.decodeResource(res, R.drawable.skeleton_image);
    }

}
