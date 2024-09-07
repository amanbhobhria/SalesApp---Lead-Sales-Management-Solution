package com.data.atul_auto_sales.Utills;

import android.graphics.drawable.GradientDrawable;

public class RoundView extends GradientDrawable {

    public static float[] getRadius(float value) {
        return new float[]{value, value, value, value, value, value, value, value};
    }

    public static float[] getRadiusLeft(float value) {
        return new float[]{0, 0, value, value, value, value,0, 0};
    }

    public static float[] getRadiusRight(float value) {
        return new float[]{value, value, 0,0, 0, 0,value,value};
    }


    public RoundView(int pStartColor, float[] radius) {
        super(Orientation.BOTTOM_TOP, new int[]{pStartColor, pStartColor, pStartColor});
        setShape(GradientDrawable.RECTANGLE);
        setCornerRadii(radius);

    }

    public RoundView(int pStartColor, int pMiddleColor, int pEndColor, float[] radius) {
        super(Orientation.BOTTOM_TOP, new int[]{pStartColor, pMiddleColor, pEndColor});
        setShape(GradientDrawable.RECTANGLE);
        setOrientation(Orientation.TOP_BOTTOM);
        setCornerRadii(radius);

    }

    public RoundView(int pStartColor, float[] radius, boolean isStroke, int pStrokeColor) {
        super(Orientation.BOTTOM_TOP, new int[]{pStartColor, pStartColor, pStartColor});
        setShape(GradientDrawable.RECTANGLE);
        setCornerRadii(radius);

        if (isStroke) {
            setStroke(3, pStrokeColor);
        }

    }
}