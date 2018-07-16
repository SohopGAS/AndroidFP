package com.shenkar.galargov.androidfp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AnimatedView extends View {
    private static final int HOR_SPEED = 10;
    private int radius = 50;
//    private mDeltaY = 10;
    private long l = getWidth() / 4, r = (getWidth() - getWidth() / 4);
    Paint paint;
    private int mHorSpeed;
    private long mTimeOfDown;
    // the x location of the car at the time of touch down
    private float mXonDown;

    public AnimatedView(Context context) {
        super(context);
        init();
    }

    public AnimatedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public AnimatedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mXonDown = w / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        if (event.getAction() == event.ACTION_DOWN) {
            if (x < getWidth() / 2) {
                mHorSpeed = -HOR_SPEED;
            }
            else {
                mHorSpeed = HOR_SPEED;
            }

            mTimeOfDown = System.currentTimeMillis();
            postInvalidateOnAnimation();

            return true;
        }


        //PO
        if (event.getAction() == event.ACTION_MOVE) {

        }

        if (event.getAction() == event.ACTION_UP) {

            mXonDown = Calculations.calculateCarLocation(System.currentTimeMillis() - mTimeOfDown, mXonDown, mHorSpeed);
            mHorSpeed = 0;

        }
        // onMoveEvent(x); // todo: relevant only if move will change car direction

        return super.onTouchEvent(event);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        long x = mHorSpeed == 0 ?
                (long) mXonDown :
                Calculations.calculateCarLocation(System.currentTimeMillis() - mTimeOfDown, mXonDown, mHorSpeed);

        // Keep the ball / car in the screen (check X limits: between 0 to getWidth).
        if (x < radius)
            x = radius;
        if (x > getWidth() - radius)
            x = getWidth() - radius;

        drawCar(canvas, x);

        drawRoad(canvas, l, r);


        if (mHorSpeed != 0)
            postInvalidateOnAnimation();
        Log.d("TTT ", "x is: " + x);
    }


    private void drawCar(Canvas canvas, long x) {
        canvas.drawCircle(x, getHeight() - (getHeight() / 4), radius, paint);
    }

    // This implementation is static, need to use left and right.
    private void drawRoad(Canvas canvas, long left, long right) {
        canvas.drawLine( getWidth() / 4,0, getWidth() / 4, getHeight(), paint);
        canvas.drawLine( (getWidth() - getWidth() / 4),0,getWidth() - getWidth() / 4, getHeight(), paint);
    }


    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        // cannonPaint = new Paint();
    }
}
