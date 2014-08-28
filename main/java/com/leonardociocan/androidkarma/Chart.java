package com.leonardociocan.androidkarma;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        draw(new int[]{});
        super.onDraw(canvas);
    }

    public void draw(int[] values){
        Paint pt = new Paint(Paint.ANTI_ALIAS_FLAG);
        pt.setStrokeWidth(2.5f);

        if(values.length == 0)return;
        int min = values[0];
        int max = values[0];
        for(int x =1;x<values.length;x++){
            if(values[x] < min)min=values[x];
            if(values[x] > max)max = values[x];
        }

        pt.setColor(getResources().getColor(R.color.orange));

        int lastX = getWidth();
        int lastY = getHeight();
        float w = getWidth() / values.length;
        for(int x = 0; x< values.length;x++){
            int span = max - min;
            int v = values[x] / span;
            float nx = getWidth() - x * w;
            float ny = getHeight() - v * getHeight();
            cnv.drawLine( lastX, lastY , nx , ny , pt);
        }


    }
}
