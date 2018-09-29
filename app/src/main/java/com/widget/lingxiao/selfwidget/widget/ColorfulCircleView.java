package com.widget.lingxiao.selfwidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ColorfulCircleView extends View{
    private static final String TAG = ColorfulCircleView.class.getSimpleName();
    private Paint mPaint,mColorFullPaint;
    private int mCircleColor;
    private int mWidth,mHeight;
    private int mRadius = 80;
    private int mSpeed = 2;
    public ColorfulCircleView(Context context) {
        this(context,null);
    }

    public ColorfulCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorfulCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        setBackgroundColor(Color.BLUE);

        mColorFullPaint = new Paint();
        mColorFullPaint.setColor(Color.parseColor("#FF4081"));
        mColorFullPaint.setStyle(Paint.Style.FILL);
        mColorFullPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        Path defPath = new Path();
        defPath.addCircle(0,0,mRadius,Path.Direction.CW);
        canvas.drawPath(defPath,mPaint);
        drawInCiracle(canvas,defPath);
    }

    private int progress = 0;
    private int style = 0;
    private void drawInCiracle(Canvas canvas, Path defPath) {
        //canvas.save();
        Path path = new Path();
        if (progress < 2*mRadius){
            if (style == 0){
                path.addCircle(0,-mRadius,progress, Path.Direction.CW);
            }else if (style == 1){
                path.addCircle(0,mRadius,progress, Path.Direction.CW);
                mColorFullPaint.setColor(Color.parseColor("#AB47BC"));
            }else if (style == 2){
                path.addCircle(-mRadius,0,progress, Path.Direction.CW);
                mColorFullPaint.setColor(Color.parseColor("#673AB7"));
            }else if (style == 3){
                path.addCircle(mRadius,0,progress, Path.Direction.CW);
                mColorFullPaint.setColor(Color.parseColor("#2196F3"));
            }
            path.op(defPath, Path.Op.INTERSECT);
            canvas.drawPath(path,mColorFullPaint);
            //canvas.drawCircle(0,mRadius,progress,mColorFullPaint);
            progress += mSpeed;
            Log.e(TAG, "progress: "+progress);
        }else {
            mPaint.setColor(mColorFullPaint.getColor());
            style++;
            progress = 0;
        }
        invalidate();
        //canvas.restore();
    }
}
