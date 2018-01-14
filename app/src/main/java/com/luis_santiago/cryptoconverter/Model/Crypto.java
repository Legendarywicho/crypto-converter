package com.luis_santiago.cryptoconverter.Model;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/13/18.
 */

public class Crypto {
    private String name;
    private double value;
    private int drawable;
    private String unit;

    public Crypto(String name, double value, int drawable, String unit) {
        this.name = name;
        this.value = value;
        this.drawable = drawable;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getUnit() {
        return unit;
    }
}
