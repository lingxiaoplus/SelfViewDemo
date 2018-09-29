package com.widget.lingxiao.selfwidget.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.widget.lingxiao.selfwidget.R;

public class HuaJiLoadingView extends View{
    private static final String TAG = "HuaJiLoadingView";
    private Paint mPaint;
    private int mWidth,mHeight;
    private Bitmap mBitmap;
    private float mDegress;
    private float mScaleWidth;
    private float mScaleHeight;
    private float mScale;

    public HuaJiLoadingView(Context context) {
        this(context,null);
    }

    public HuaJiLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HuaJiLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Drawable drawable = context.getResources().getDrawable(R.drawable.huaji);
        //Bitmap mBitmap = drawableToBitmap(drawable);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.huaji);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    Matrix matrix = new Matrix();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        /*canvas.drawBitmap(mBitmap,this.getWidth()/2-mBitmap.getWidth()/2,
                this.getHeight()/2-mBitmap.getHeight()/2, null);*/


        matrix.postTranslate(-mBitmap.getWidth()/2, -mBitmap.getHeight()/2);
        matrix.postRotate(mDegress);
        matrix.postScale(mScale,mScale);
        //matrix.postTranslate(this.getWidth()/2,this.getHeight()/2);
        canvas.drawBitmap(mBitmap, matrix, null);
        matrix.reset();
        startTrans();
    }

    private TimeInterpolator timeInterpolator = new LinearInterpolator();
    private ValueAnimator valueAnimator;
    private void startTrans(){
        if (valueAnimator != null){
            if (valueAnimator.isRunning()){
                return;
            }
            valueAnimator.start();
        }else {
            valueAnimator = ValueAnimator.ofFloat(0f,360f).setDuration(2000);
            valueAnimator.setInterpolator(timeInterpolator);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mDegress = (float) valueAnimator.getAnimatedValue();
                    if (mDegress < 180){
                        mScale = 1f - mDegress / 360;
                    }else {
                        mScale = mDegress / 360;
                    }

                    //及计算图片缩放比例
                    /*mScaleWidth = scale * mBitmap.getWidth();
                    mScaleHeight = scale * mBitmap.getHeight();
                    Log.e(TAG, "宽比例: "+scale+"  高比例："+mScaleHeight);*/
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
                Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
