package com.alexcrist.compactadventure.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.alexcrist.compactadventure.R;

public class Drawer {

    private World world;
    private int w;
    private int h;
    private Bitmap playerImage;

    public Drawer(World world, Context context) {
        this.world = world;
        initBitmaps(context);
    }

    public void initSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint(); // Placeholder for bitmaps
        paint.setColor(Color.BLACK);

        for (Entity entity : world.entities) {
            // Translating positions and radii:
            //   0,0   = center
            //   1,1   = top right
            //   -1,-1 = bottom left
            //   radius of .2 = object's radius is 20% of screen width
            float x = entity.x * w / 2 + w / 2;
            float y = -entity.y * h / 2 + h / 2;
            float angle = entity.angle;
            float radius = entity.radius * w;

            switch (entity.type()) {

                case Entity.PLAYER_TYPE:
                    drawBitmap(canvas, playerImage, x, y, angle);
                    break;
            }

            canvas.drawCircle(x, y, radius, paint);
        }
    }

    private void drawBitmap(Canvas canvas, Bitmap image, float x, float y, float angle) {
        canvas.save();
        canvas.rotate(-angle + 90, x, y);
        canvas.drawBitmap(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        canvas.restore();
    }

    private void initBitmaps(Context context) {
        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_image);
    }
}
