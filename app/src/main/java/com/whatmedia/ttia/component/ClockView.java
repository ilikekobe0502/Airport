package com.whatmedia.ttia.component;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View {
    private Paint p;
    int c, h, m, s;

    public ClockView(Context context) {
        super(context);
        c = Color.parseColor("#F44336");
        h = 17;
        m = 0;
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = Color.parseColor("#F44336");
        h = 17;
        m = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        int center_w = (getMeasuredWidth() / 2);
        int center_h = (getMeasuredHeight() / 2);
        int len = (getMeasuredHeight() / 2) - 3;
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
//        p.setStyle(Paint.Style.FILL);
//        p.setColor(c);
//        canvas.drawCircle(center_w, center_h, len, p);
//        p.setColor(Color.WHITE);
//        canvas.drawCircle(center_w, center_h, (float) (int) (len * 0.8), p);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth((float) (len * 0.03));
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setColor(c);

        canvas.drawLine(center_w, center_h, center_w + getSecondX(len), center_h + getSecondY(len), p);


        p.setColor(Color.BLACK);
        //p.setColor(getResources().getColor(R.color.textColorSecondary));
        p.setStrokeWidth((float) (len * 0.04));
        canvas.drawLine(center_w, center_h, center_w + getMinuteX(len), center_h + getMinuteY(len), p);
        //p.setColor(getResources().getColor(R.color.textColorPrimary));
        p.setStrokeWidth((float) (len * 0.045));
        canvas.drawLine(center_w, center_h, center_w + getHourX(len), center_h + getHourY(len), p);
    }

    public void setHour(int h) {
        this.h = h;
    }

    public void setMinute(int m) {
        this.m = m;
    }

    public void setSecond(int s) {
        this.s = s;
        invalidate();
    }

    public void setClockColor(int c) {
        this.c = c;
    }

    private float getSecondX(int l) {
        double angle = Math.toRadians(s * 6);
        return (float) (0.7 * l * Math.sin(angle));
    }

    private float getSecondY(int l) {
        double angle = Math.toRadians(s * 6);
        return -(float) (0.7 * l * Math.cos(angle));
    }

    private float getMinuteX(int l) {
        int c = 0;
        if (m < 15) {
            c = m + 45;
        } else {
            c = m - 15;
        }
        double angle = Math.toRadians(c * 6);
        return (float) (0.6 * l * Math.cos(angle));
    }

    private float getMinuteY(int l) {
        int c = 0;
        if (m < 15) {
            c = m + 45;
        } else {
            c = m - 15;
        }
        double angle = Math.toRadians(c * 6);
        return (float) (0.6 * l * Math.sin(angle));
    }

    private float getHourX(int l) {
        double angle = Math.toRadians(((h * 60) + m) / 2 - 90);
        return (float) (0.4 * l * Math.cos(angle));
    }

    private float getHourY(int l) {
        double angle = Math.toRadians(((h * 60) + m) / 2 - 90);
        return (float) (0.4 * l * Math.sin(angle));
    }


}