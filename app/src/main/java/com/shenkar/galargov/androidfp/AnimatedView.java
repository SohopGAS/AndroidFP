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
    private static final int HOR_SPEED = 3;
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
            } else {
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
        long t1 = System.currentTimeMillis();
        long x = mHorSpeed == 0 ?
                (long) mXonDown :
                Calculations.calculateCarLocation(t1 - mTimeOfDown, mXonDown, mHorSpeed);
        drawCar(canvas, x);
        if (mHorSpeed != 0)
            postInvalidateOnAnimation();
        Log.d("TTT ", "x is: " + x);
    }

    private void drawCar(Canvas canvas, long x) {

        canvas.drawCircle(x, getHeight() - 30, 10, paint);
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        // cannonPaint = new Paint();
    }
}
