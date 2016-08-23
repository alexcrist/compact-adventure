package com.alexcrist.compactadventure.core;

import android.util.Log;
import android.view.MotionEvent;

public class MotionHandler {

    private World world;

    private boolean leftReady;
    private boolean rightReady;

    private float leftY;
    private float rightY;

    private int w;
    private int h;

    private float SWIPE_THRESHOLD;

    public MotionHandler(World world) {
        this.world = world;
    }

    // Scale to screen size
    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;
        this.SWIPE_THRESHOLD = h / 10;
    }

    // Interpret and handle motion events from user touching screen
    public void handleMotion(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getActionMasked()) {

            // Finger touches down on screen
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (x < w / 2) {
                    leftReady = true;
                    leftY = y;
                } else {
                    rightReady = true;
                    rightY = y;
                }
                break;

            // Finger moves across screen
            case MotionEvent.ACTION_MOVE:
                Log.i("Action", "move");
                float dY;
                if (x < w / 2 && leftReady) {
                    dY = y - leftY;
                } else if (x >= w / 2 && rightReady) {
                    dY = y - rightY;
                } else {
                    break;
                }

                if (Math.abs(dY) > SWIPE_THRESHOLD) {
                    Log.i("Action", "2");
                    if (x < w / 2) {
                        if (dY < 0) {
                            world.leftUpAction();
                        } else {
                            world.leftDownAction();
                        }
                        leftReady = false;
                    } else {
                        if (dY < 0) {
                            world.rightUpAction();
                        } else {
                            world.rightDownAction();
                        }
                        rightReady = false;
                    }
                }
                break;
        }
    }
}
