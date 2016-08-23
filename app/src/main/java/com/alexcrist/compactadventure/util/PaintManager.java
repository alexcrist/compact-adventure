package com.alexcrist.compactadventure.util;

import android.graphics.Paint;

public class PaintManager {

    private static final int BAR_BORDER_STROKE = 12;

    public Paint shadow;
    public Paint invincibility;
    public Paint healthBar;
    public Paint healthBarBG;
    public Paint enemiesBar;
    public Paint enemiesBarBG;
    public Paint barBorder;
    public Paint successCover;
    public Paint failureCover;

    public PaintManager() {
        this.shadow = new Paint();
        this.invincibility = new Paint();
        this.healthBar = new Paint();
        this.healthBarBG = new Paint();
        this.enemiesBar = new Paint();
        this.enemiesBarBG = new Paint();
        this.barBorder = new Paint();
        this.successCover = new Paint();
        this.failureCover = new Paint();

        this.shadow.setARGB(100, 100, 100, 100);
        this.invincibility.setARGB(150, 255, 0, 0);
        this.healthBar.setARGB(255, 0, 200, 0);
        this.healthBarBG.setARGB(255, 200, 0, 0);
        this.enemiesBar.setARGB(255, 0, 0, 200);
        this.enemiesBarBG.setARGB(255, 100, 100, 100);
        this.barBorder.setARGB(255, 0, 0, 0);
        this.barBorder.setStyle(Paint.Style.STROKE);
        this.barBorder.setStrokeWidth(BAR_BORDER_STROKE);
        this.successCover.setARGB(0, 255, 255, 255);
        this.failureCover.setARGB(0, 0, 0, 0);
    }
}
