package com.leonardociocan.androidkarma;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class Chart extends View {


    public Chart(Context context) {
        super(context);
    }

    public Chart(Context context, AttributeSet st) {
        super(context, st);
    }

    Canvas cnv;
    @Override
    protected void onDraw(Canvas canvas) {
        cnv = canvas;
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);



        if(cnv == null)return;
        Paint pt = new Paint(Paint.ANTI_ALIAS_FLAG);
        pt.setStrokeWidth(5.5f);
        pt.setStyle(Paint.Style.STROKE);


        //setBackgroundColor(getResources().getColor(R.color.green));

        int[] values = Core.LogValues();

        if(values.length == 0)return;
        int min = values[0];
        int max = values[0];
        for(int x =1;x<values.length;x++){
            if(values[x] < min)min=values[x];
            if(values[x] > max)max = values[x];
        }

        pt.setColor(getResources().getColor(R.color.orange));

        float w = getWidth() / values.length;


        PointF initial = getPoint(values , values.length-1 , max,min,w);
        float lastX = initial.x;
        float lastY = initial.y;
        float span = max - min;

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.pink_error));

        p.setStrokeWidth(1.5f);

        p.setAlpha(255);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        Path px = new Path();
        px.moveTo(0,(max / span) * getHeight() );
        px.lineTo(getWidth(),(max / span) * getHeight());
        cnv.drawPath(px, p);
        //cnv.drawRect(0.0f,(max/span) * getHeight(),(float)getWidth(),(float)getHeight() , p);

        Path path = new Path();
        path.moveTo(initial.x,initial.y);

        for(int x = values.length - 1; x>=0;x--){

            float v = values[x] / span;
            float nx =  x * w;
            float ny = (getHeight() - v * getHeight()) * (max/span);
            //cnv.drawLine( lastX, lastY , nx , ny , pt);
            path.lineTo(nx,ny);
            lastX = nx;
            lastY = ny;
        }
        cnv.drawPath(path,  pt);


    }

    public PointF getPoint(int[] values , int x , float max , float min , float w){

        float span = max - min;
        float v = values[x] / span;
        float nx =  x * w;
        float ny = (getHeight() - v * getHeight()) * (max/span);
        return new PointF(nx,ny);
    }

}
