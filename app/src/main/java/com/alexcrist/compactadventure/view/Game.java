package com.alexcrist.compactadventure.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.alexcrist.compactadventure.model.Drawer;
import com.alexcrist.compactadventure.model.MotionHandler;
import com.alexcrist.compactadventure.model.World;

public class Game extends View {

    private World world;
    private Drawer drawer;
    private MotionHandler motionHandler;

    public Game(Context context) {
        super(context);
    }

    public Game(Context context, int level) {
        this(context);
        world = new World(level);
        drawer = new Drawer(world, context);
        motionHandler = new MotionHandler(world);
    }

    // Called when the view is first created (or its size changes)
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        drawer.initSize(w, h);
        motionHandler.initSize(w, h);
    }

    // Called to draw the view. Also called from invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        drawer.draw(canvas); // Draw the world
        world.update();      // Update the world
        invalidate();        // Repeat
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionHandler.handleMotion(motionEvent);
        return true;
    }
}
