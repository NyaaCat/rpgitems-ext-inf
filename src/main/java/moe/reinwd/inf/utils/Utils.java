package moe.reinwd.inf.utils;

import cat.nyaa.infiniteinfernal.ui.impl.VarRage;

public class Utils {

    public static double damageFunc(double damage, double factor, VarRage rage) {
        if (damage < 0) return 0;
        double x = damage + 1;
        double log = Math.log(x);
        double percentileFactor = Math.max(1 - (rage.getValue() / Math.max(rage.getMaxValue(), 0.01)), 0.1);
        return Math.max(4 * log * factor * percentileFactor, damage * 0.01);
    }
}
