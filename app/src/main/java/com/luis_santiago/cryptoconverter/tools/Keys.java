package com.luis_santiago.cryptoconverter.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/13/18.
 */

public class Keys {
    public static final String KEY_UNIT_COIN = "unit";
    public static final String KEY_VALUE_COIN = "value";

    public static String getPreferenceCurrency(Context context){
        SharedPreferences sharedConfig = PreferenceManager.getDefaultSharedPreferences(context);
        String localCurrency = sharedConfig.getString("local_unit", "MXN");
        return localCurrency;
    }
}
