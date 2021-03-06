package com.yy.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ProgressBar extends View {


    private Paint paint0;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private Paint paint6;

    private float cx0 = -10;
    private float cx1 = -10;
    private float cx2 = -10;
    private float cx3 = -10;
    private float cx4 = -10;
    private float cx5 = -10;
    private float cx6 = -10;

    private long delay = 100;
    private long duration = 1500;
    private float start = -10;
    private float end ;
    
    private ObjectAnimator animator0;
    private ObjectAnimator animator1;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;
    private ObjectAnimator animator4;
    private ObjectAnimator animator5;
    private ObjectAnimator animator6;
    private boolean isRunning = false;

    public ProgressBar(Context context) {
        super(context);
//        init();
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    private void init() {

        paint0 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint0.setColor(0xFFFF0000);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(0xFFFF7F00);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(0xFFFFFF00);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setColor(0xFF00FF00);

        paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint4.setColor(0xFF00FFFF);
        
        paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint5.setColor(0xFF0000FF);
        
        paint6 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint6.setColor(0xFF8B00FF);

        animator0 = ObjectAnimator.ofFloat(this, "cx0", start, end);
        animator0.setDuration(duration);
        animator0.setInterpolator(new DecelerateAccelerateInterpolator());
        animator0.start();

        animator1 = ObjectAnimator.ofFloat(this, "cx1", start, end);
        animator1.setDuration(duration);
        animator1.setStartDelay(delay);
        animator1.setInterpolator(new DecelerateAccelerateInterpolator());


        animator2 = ObjectAnimator.ofFloat(this, "cx2", start, end);
        animator2.setDuration(duration);
        animator2.setStartDelay(delay * 2);
        animator2.setInterpolator(new DecelerateAccelerateInterpolator());

        animator3 = ObjectAnimator.ofFloat(this, "cx3", start, end);
        animator3.setDuration(duration);
        animator3.setStartDelay(delay * 3);
        animator3.setInterpolator(new DecelerateAccelerateInterpolator());

        animator4 = ObjectAnimator.ofFloat(this, "cx4", start, end);
        animator4.setDuration(duration);
        animator4.setStartDelay(delay * 4);
        animator4.setInterpolator(new DecelerateAccelerateInterpolator());
        
        animator5 = ObjectAnimator.ofFloat(this, "cx5", start, end);
        animator5.setDuration(duration);
        animator5.setStartDelay(delay * 5);
        animator5.setInterpolator(new DecelerateAccelerateInterpolator());

        animator6 = ObjectAnimator.ofFloat(this, "cx6", start, end);
        animator6.setDuration(duration);
        animator6.setStartDelay(delay * 6);
        animator6.setInterpolator(new DecelerateAccelerateInterpolator());       
        animator6.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                start();
                Log.i("onAnimationEnd", "");
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.i("widthSize ", widthSize + "");
        int measuredWidth, measuredHeight;

        measuredWidth = widthSize;
        measuredHeight = heightSize;

        setMeasuredDimension(measuredWidth, measuredHeight);

        end = -start + measuredWidth;
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (!isRunning) {
            start();
            isRunning = true;
        }

        canvas.drawCircle(cx0, 100, 10, paint0);
        canvas.drawCircle(cx1, 100, 10, paint1);
        canvas.drawCircle(cx2, 100, 10, paint2);
        canvas.drawCircle(cx3, 100, 10, paint3);
        canvas.drawCircle(cx4, 100, 10, paint4);
        canvas.drawCircle(cx5, 100, 10, paint5);
        canvas.drawCircle(cx6, 100, 10, paint6);
    }

    public void start() {
        animator0.start();
        animator1.start();
        animator2.start();
        animator3.start();
        animator4.start();
        animator5.start();
        animator6.start();
    }

    public float getCx0() {
        return cx0;
    }

    public void setCx0(float cx0) {
        this.cx0 = cx0;
        invalidate();
    }

    public float getCx1() {
        return cx1;
    }

    public void setCx1(float cx1) {
        this.cx1 = cx1;
        invalidate();
    }

    public float getCx2() {
        return cx2;
    }

    public void setCx2(float cx2) {
        this.cx2 = cx2;
        invalidate();
    }

    public float getCx3() {
        return cx3;
    }

    public void setCx3(float cx3) {
        this.cx3 = cx3;
        invalidate();
    }

    public float getCx4() {
        return cx4;
    }

    public void setCx4(float cx4) {
        this.cx4 = cx4;
        invalidate();
    }
    
    public float getCx5() {
        return cx5;
    }

    public void setCx5(float cx5) {
        this.cx5 = cx5;
        invalidate();
    }
    
    public float getCx6() {
        return cx6;
    }

    public void setCx6(float cx6) {
        this.cx6 = cx6;
        invalidate();
    }

    private class DecelerateAccelerateInterpolator implements TimeInterpolator {

        private DecelerateAccelerateInterpolator() {

        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.asin(2 * input - 1) / Math.PI + 0.5);
        }
    }
}
