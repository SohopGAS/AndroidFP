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


//
//    RectLogic logic = new RectLogic();      // interface to calculate the bricks in both sides.
//
//    private static final String TAG = AnimatedView.class.getSimpleName();
//    private static final int CANNON_SIZE = 30;
//
//    PointF point = new PointF();
//
//
//
//    Paint paint, cannonPaint;
//    private long t0;
//    private float v0;
//    private float angleRad;
//    private float angleDeg;
//    private Path trianglePath;
//
//
//    public AnimatedView(Context context) {
//        super(context);
//        init();
//    }
//
//    public AnimatedView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//
//    public AnimatedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    public AnimatedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }
//
//    private void init() {
//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
//        cannonPaint = new Paint();
//        cannonPaint.setAntiAlias(true);
//        cannonPaint.setColor(Color.BLACK);
//        cannonPaint.setStyle(Paint.Style.FILL);
//
//        trianglePath = new Path();
//        trianglePath.moveTo(0, 0);
//        trianglePath.lineTo(CANNON_SIZE, CANNON_SIZE);
//        trianglePath.lineTo(-CANNON_SIZE, CANNON_SIZE);
//        trianglePath.close();
//    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        long now = System.currentTimeMillis();
//        logic.calculateBulletLocation(point, v0, angleRad, t0, now);
//
//        canvas.translate(CANNON_SIZE * 1.5f, getHeight() / 2);
//
//        canvas.drawCircle(point.x, point.y, CANNON_SIZE / 2, paint);
//
//        drawCannon(canvas);
//
//        if (point.x < getWidth()) {
//            // no need to continue the paint loop
//            postInvalidateOnAnimation();
//        }
//    }
//
//    private void drawCannon(Canvas canvas) {
//        int save = canvas.save();
//        // canvas.translate(0, CANNON_SIZE * 2);
//        canvas.drawPath(trianglePath, cannonPaint);
//        // canvas.translate(0, -CANNON_SIZE * 2);
//        canvas.rotate(angleDeg, 0, 0);
//        canvas.drawCircle(0, 0, CANNON_SIZE, cannonPaint);
//        canvas.drawRoundRect(0, CANNON_SIZE / 2,
//                CANNON_SIZE * 2, -CANNON_SIZE / 2,
//                CANNON_SIZE / 5, CANNON_SIZE / 5, cannonPaint);
//        canvas.restoreToCount(save);
//    }
//
//
//    public void setT0(long t0) {
//        this.t0 = t0;
//        // trigger an onDraw() cycle
//        postInvalidateOnAnimation();
//    }
//
//    public void setV0(float v0) {
//        this.v0 = v0;
//        // trigger an onDraw() cycle
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * @param angleDegrees 0..360
//     */
//    public void setAngle(float angleDegrees) {
//        this.angleDeg = angleDegrees;
//        this.angleRad = (float) Math.toRadians(angleDegrees);
//        // trigger an onDraw() cycle
//        postInvalidateOnAnimation();
//    }
}
