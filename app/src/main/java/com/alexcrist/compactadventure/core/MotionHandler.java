package com.alexcrist.compactadventure.core;

import android.view.MotionEvent;

public class MotionHandler {

    private World world;

    private float yStart1; // Initial y value of finger #1
    private float yStart2; // Initial y value of finger #2 (2 simultaneous touches max)

    private boolean ready1; // Boolean value, if equal to true, finger #1 has not yet swiped
    private boolean ready2; // Boolean value, if equal to true, finger #2 has not yet swiped

    private int w;
    private int h;

    private float SWIPE_THRESHOLD;

    public MotionHandler(World world) {
        this.world = world;
        this.ready1 = true;
        this.ready2 = true;
    }

    // Scale to screen size
    public void scaleToScreen(int w, int h) {
        this.w = w;
        this.h = h;
        this.SWIPE_THRESHOLD = h / 10;
    }

    // Interpret and handle motion events from user touching screen
    public void handleMotion(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int actionIndex = motionEvent.getActionIndex();

        switch (motionEvent.getAction()) {

            // Finger touches screen
            case MotionEvent.ACTION_DOWN:
                if (actionIndex == 0) {
                    yStart1 = y;
                    ready1 = true;
                } else if (actionIndex == 1) {
                    yStart2 = y;
                    ready2 = true;
                }
                break;

            // Finger moves across screen
            case MotionEvent.ACTION_MOVE:

                // Determine swipe distance
                float dY;
                if (actionIndex == 0 && ready1) {
                    dY = y - yStart1;
                } else if (actionIndex == 1 && ready2) {
                    dY = y - yStart2;
                } else {
                    break;
                }

                // Finger has swiped
                if (Math.abs(dY) > SWIPE_THRESHOLD) {

                    // Upward swipe, left half
                    if (dY < 0 && x < w / 2) {
                        world.leftUpAction();

                    // Upward swipe, right half
                    } else if (dY < 0 && x > w / 2) {
                        world.rightUpAction();

                    // Downward swipe, left half
                    } else if (dY > 0 && x < w / 2) {
                        world.leftDownAction();

                    // Downward swipe, right half
                    } else {
                        world.rightDownAction();
                    }

                    // The user must lift finger before swiping again
                    if (actionIndex == 0) {
                        ready1 = false;
                    } else {
                        ready2 = false;
                    }
                }

                break;
        }
    }
}
