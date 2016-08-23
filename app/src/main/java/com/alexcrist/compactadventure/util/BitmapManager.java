package com.alexcrist.compactadventure.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alexcrist.compactadventure.R;

public class BitmapManager {

    public Bitmap player;
    public Bitmap sword1;
    public Bitmap skeleton;
    public Bitmap balloon;
    public Bitmap hp;
    public Bitmap progress;

    public BitmapManager(Resources res) {
        player = BitmapFactory.decodeResource(res, R.drawable.player_image);
        sword1 = BitmapFactory.decodeResource(res, R.drawable.sword_1_image);
        skeleton = BitmapFactory.decodeResource(res, R.drawable.skeleton_image);
        balloon = BitmapFactory.decodeResource(res, R.drawable.balloon_image);
        hp = BitmapFactory.decodeResource(res, R.drawable.hp_image);
        progress = BitmapFactory.decodeResource(res, R.drawable.progress_image);
    }

    // Scale bitmaps to screen size
    public void scaleToScreen(int w, int h) {
        // TODO
    }
}
