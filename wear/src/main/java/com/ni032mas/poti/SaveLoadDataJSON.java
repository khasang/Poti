package com.ni032mas.poti;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SaveLoadDataJSON<T> {
    private final String NAME_SHARED_PREF = "poti";
    private final String DATA = "DATA";
    T appData;

    public SaveLoadDataJSON(Context context) {
        this.context = context;
    }

    private SharedPreferences mPrefs;
    private Gson gson;
    private String json;
    Context context;

    public void saveJSON(T timerData) {
        mPrefs = context.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        gson = new Gson();
        json = gson.toJson(timerData);
        prefsEditor.putString(DATA, json);
        prefsEditor.commit();
    }

    public T loadJSON() {
        mPrefs = context.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE);
        gson = new Gson();
        json = mPrefs.getString(DATA, "");
        try {
            return gson.fromJson(json,  (Type) appData.getClass());
        } catch (Throwable t) {
            return null;
        }
    }
}
