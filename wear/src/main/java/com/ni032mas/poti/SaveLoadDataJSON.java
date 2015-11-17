package com.ni032mas.poti;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SaveLoadDataJSON<T> {
    String mNameData;
    T appData;

    public SaveLoadDataJSON(Context context) {
        this.context = context;
    }

    private SharedPreferences mPrefs;
    private Gson gson;
    private String json;
    Context context;

    public void saveJSON(T timerData, String dataTAG) {
        mPrefs = context.getSharedPreferences(mNameData, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        gson = new Gson();
        json = gson.toJson(timerData);
        prefsEditor.putString(dataTAG, json);
        prefsEditor.commit();
    }

    public T loadJSON(String dataTAG) {
        mPrefs = context.getSharedPreferences(mNameData, context.MODE_PRIVATE);
        gson = new Gson();
        json = mPrefs.getString(dataTAG, "");
        return gson.fromJson(json,  (Type) appData.getClass());
    }
}
