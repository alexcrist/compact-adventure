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

    public void initSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint(); // Placeholder for bitmaps
        paint.setColor(Color.RED);

        for (Entity entity : world.entities) {
            if (entity.alive) {
                // Translating positions and radii:
                //   0,0   = center
                //   1,1   = top right
                //   -1,-1 = bottom left
                //   radius of .2 = object's radius is 20% of screen width
                float x = entity.x * w / 2 + w / 2;
                float y = -entity.y * h / 2 + h / 2;
                float radius = entity.radius * w / 2;

                float angle = entity.angle;

                switch (entity.type()) {

                    case Entity.SKELETON_TYPE:
                        drawBitmap(canvas, skeletonImage, x, y, angle);
                        break;

                    case Entity.PLAYER_TYPE:
                        drawBitmap(canvas, sword1Image, x, y, angle);
                        drawBitmap(canvas, playerImage, x, y, angle);
                        break;
                }

                canvas.drawCircle(x, y, radius, paint);
            }
        }
    }

    private void drawBitmap(Canvas canvas, Bitmap image, float x, float y, float angle) {
        canvas.save();
        canvas.rotate(-angle + 90, x, y);
        canvas.drawBitmap(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        canvas.restore();
    }

    private void initBitmaps(Resources res) {
        playerImage = BitmapFactory.decodeResource(res, R.drawable.player_image);
        sword1Image = BitmapFactory.decodeResource(res, R.drawable.sword_1_image);
        skeletonImage = BitmapFactory.decodeResource(res, R.drawable.skeleton_image);
    }

}
