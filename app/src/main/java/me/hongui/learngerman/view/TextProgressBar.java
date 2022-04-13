package me.hongui.learngerman.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import me.hongui.learngerman.R;

public class TextProgressBar extends View {
    private static final float HEIGHT=16F;
    private float animProgress;
    private Animator animator;
    private float progress;
    private String content;
    private Rect bound;
    private Paint paint;
    private TextPaint textPaint;

    public TextProgressBar(Context context) {
        super(context);
        init();
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT, getContext().getResources().getDisplayMetrics());
        setMeasuredDimension(widthMeasureSpec,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius=getHeight()/2F;
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3F, getContext().getResources().getDisplayMetrics());
        float current = animProgress * getWidth();
        paint.setColor(ContextCompat.getColor(getContext(),R.color.alphaGrey));
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),radius,radius,paint);
        paint.setColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        canvas.drawRoundRect(0, 0, current, getHeight(), radius, radius, paint);
        if(current+2*padding+bound.width()>=getWidth()){
            textPaint.setColor(Color.WHITE);
            current=getWidth()-2*padding-bound.width();
        }
        canvas.drawText(content,current+padding,radius-bound.centerY(),textPaint);
    }

    private void init(){
        int color=ContextCompat.getColor(getContext(), R.color.colorAccent);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(color);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10F, getContext().getResources().getDisplayMetrics()));
        animator = ValueAnimator.ofFloat(0, progress);
        ((ValueAnimator) animator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animProgress=progress*animation.getAnimatedFraction();
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2000);
    }

    public void setProgress(float progress){
        this.progress=progress;
        animProgress=0f;
        content = String.format("%.02f", progress*100)+"%";
        if(null==bound){
            bound = new Rect();
        }
        textPaint.getTextBounds(content, 0, content.length(), bound);
        invalidate();
        animator.start();
    }
}
