package com.tayfunisik.firebaseauthlogin;

import android.view.animation.Interpolator;

public class MyBounceInterpolator implements Interpolator {

    double mAmplitude = 1;
    double mFrequency = 10;

    public MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        double amplitude = mAmplitude;
        if (amplitude == 0) { amplitude = 0.05; }
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}
