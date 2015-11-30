package com.ni032mas.poti;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SaveLoadDataJSON<T> {
    //    String mNameData;
    T appData;
    private SharedPreferences mPrefs;
    private Gson gson;
    private String json;
    Context context;
    private static final String DATA_FILE_NAME = "data";

    public SaveLoadDataJSON(Context context) {
        this.context = context;
        appData = (T) new AppData();
    }

    public void saveJSON(T timerData, String dataTAG) {
        mPrefs = context.getSharedPreferences(DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        gson = new Gson();
        json = gson.toJson(timerData);
        prefsEditor.putString(dataTAG, json);
        prefsEditor.apply();
    }

    public T loadJSON(String dataTAG) {
        mPrefs = context.getSharedPreferences(DATA_FILE_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        if (!mPrefs.contains(dataTAG)) {
            return null;
        } else {
            json = mPrefs.getString(dataTAG, "");
            return gson.fromJson(json, (Type) appData.getClass());
        }
    }
}
