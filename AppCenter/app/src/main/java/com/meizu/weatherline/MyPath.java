package com.meizu.weatherline;

import android.graphics.Path;

/**
 * Created by root on 15-1-22.
 */
public class MyPath extends Path {

    public void myQuadTo(float x1,float y1,float x2, float y2){

        float x0 = (x1+x2)/2;
        float y0 = (y1+y2)/2;

        float x10 = (x0+x1)/2;
        float y10 = y1;

        quadTo(x10,y10,x0,y0);

        float x02 = (x0+x2)/2;
        float y02 = y2;

        quadTo(x02,y02,x2,y2);
    }

    public void myCircleLineto(float x1,float y1,float x2,float y2){

        addCircle(x1,y1,5f,Direction.CW);
        moveTo(x1,y1);
        lineTo(x2,y2);

    }
}
