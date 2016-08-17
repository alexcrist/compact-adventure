package com.alexcrist.compactadventure.core;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;

import com.alexcrist.compactadventure.util.WorldFactory;

public class Game extends View {

    private World world;
    private Drawer drawer;
    private MotionHandler motionHandler;

    private boolean dialogDisplayed;
    private AlertDialog successDialog;
    private AlertDialog failureDialog;

    public Game(Context context) {
        super(context);
    }

    public Game(Context context, int level, AlertDialog successDialog, AlertDialog failureDialog) {
        this(context);
        WorldFactory worldFactory = new WorldFactory();

        this.world = worldFactory.generateWorld(level);
        this.drawer = new Drawer(world, context.getResources());
        this.motionHandler = new MotionHandler(world);

        this.dialogDisplayed = false;
        this.successDialog = successDialog;
        this.failureDialog = failureDialog;
    }

    // Called when the view is first created (or its size changes)
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        world.scaleToScreen(w, h);
        drawer.scaleToScreen(w, h);
        motionHandler.scaleToScreen(w, h);
    }

    // Called to draw the view. Also called from invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        drawer.draw(canvas); // Draw the world
        world.update();      // Update the world
        handleGameOver();    // If game is over, display a modal
        invalidate();        // Repeat
    }

    // Called to handle touch events to the screen
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionHandler.handleMotion(motionEvent);
        return true;
    }

    // If the game is over and the screen has finished fading, display a modal
    private void handleGameOver() {
        if (!dialogDisplayed) {
            if (drawer.screenFadeProgress == Drawer.SCREEN_FADE_DURATION) {
                if (world.gameStatus == World.GAME_OVER_SUCCESS) {
                    dialogDisplayed = true;
                    String msg = Integer.toString(world.maxEnemies);
                    msg = msg.equals("1") ? msg + " enemy defeated." : msg + " enemies defeated.";
                    successDialog.setMessage(msg);
                    successDialog.show();
                } else if (world.gameStatus == World.GAME_OVER_FAILURE) {
                    dialogDisplayed = true;
                    failureDialog.show();
                }
            }
        }
    }
}
