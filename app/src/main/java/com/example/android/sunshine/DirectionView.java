package com.example.android.sunshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/*
* Thanks to cchabot: http://forums.udacity.com/questions/100354717/direction-custom-view#ud853
*/
public class DirectionView extends View {
    static private int MAX_WIDTH = 100;
    static private int MAX_HEIGHT = 100;

    private Path mCircle;
    private Path mDirection;
    private Paint mPaint;
    private double mDegrees = -1;

    public DirectionView(Context context) {
        super(context);
        initCanvas();
    }

    public DirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCanvas();
    }

    public DirectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCanvas();
    }

    public void setDirection(double degrees) {
        mDegrees = degrees;
        invalidate();
    }

    private void initCanvas() {
        mCircle = new Path();
        mDirection = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void buildCirclePath() {
        int width = getWidth();
        int height = getHeight();

        mPaint.setColor(getResources().getColor(R.color.sunshine_light_blue));
        mPaint.setStyle(Paint.Style.FILL);
        mCircle.addCircle(width / 2, height / 2, Math.min(width, height) / 2, Path.Direction.CCW);
    }

    private void buildDirectionPath() {
        int width = getWidth();
        int height = getHeight();

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mDirection.reset();
        mDirection.moveTo(width / 2, height / 2);
        if (mDegrees >= 337.5 || mDegrees < 22.5) {
            //direction = "N";
            mDirection.lineTo(width / 2, 0);
        } else if (mDegrees >= 22.5 && mDegrees < 67.5) {
            //direction = "NE";
            mDirection.lineTo(width, 0);
        } else if (mDegrees >= 67.5 && mDegrees < 112.5) {
            //direction = "E";
            mDirection.lineTo(width, height / 2);
        } else if (mDegrees >= 112.5 && mDegrees < 157.5) {
            //direction = "SE";
            mDirection.lineTo(width, height);
        } else if (mDegrees >= 157.5 && mDegrees < 202.5) {
            //direction = "S";
            mDirection.lineTo(width / 2, height);
        } else if (mDegrees >= 202.5 && mDegrees < 247.5) {
            //direction = "SW";
            mDirection.lineTo(0, height);
        } else if (mDegrees >= 247.5 && mDegrees < 292.5) {
            //direction = "W";
            mDirection.lineTo(0, height / 2);
        } else if (mDegrees >= 292.5 && mDegrees < 337.5) {
            //direction = "NW";
            mDirection.lineTo(0, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = MAX_WIDTH;
        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else if (wMode == MeasureSpec.EXACTLY) {
            width = (wSize < MAX_WIDTH) ? wSize : MAX_WIDTH;
        }

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = MAX_HEIGHT;
        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else if (hMode == MeasureSpec.EXACTLY) {
            height = (hSize < MAX_HEIGHT) ? hSize : MAX_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // mDegrees is negative when view is first constructed
        if (mDegrees != -1) {
            buildCirclePath();
            canvas.drawPath(mCircle, mPaint);
            buildDirectionPath();
            canvas.drawPath(mDirection, mPaint);
        }
    }
}