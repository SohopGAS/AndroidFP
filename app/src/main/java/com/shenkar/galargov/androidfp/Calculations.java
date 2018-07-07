package com.shenkar.galargov.androidfp;

class Calculations {
    public static long calculateCarLocation(long deltaT, float x0, int velocity) {
        return ((long) (x0 + velocity * deltaT / Constants.SPEED_FACTOR));
    }
}
