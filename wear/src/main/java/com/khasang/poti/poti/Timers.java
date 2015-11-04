package com.khasang.poti.poti;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by nadezhda on 14.10.2015.
 */
public class Timers {

    private List<Timer2> items;
    //private static final String FILE_NAME = "poti.txt";

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String loadFromFile(Activity activity) {
//        FileInputStream fileInputStream = null;
//        Scanner scanner = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            fileInputStream = openFileInput(FILE_NAME);
//            if (fileInputStream != null){
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (fileInputStream != null){
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        byte[] buffer = null;
//        InputStream is;
//        String str_data = "";
//        try {
//            is = activity.getAssets().open("timers.xml");
//            int size = is.available();
//            buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            str_data = new String(buffer);
//            str_data+= " size " + size + " ";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.name = str_data;
        String myText = "";
        try {
            Resources r = activity.getResources();
            InputStream is = r.openRawResource(R.raw.timers);
            myText = convertStreamToString(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myText;
    }

    public void loadTimers(Activity activity){
        try {

            JSONObject js = new JSONObject(loadFromFile(activity));
            JSONArray jsonArray = js.getJSONArray("timers");
            items = new ArrayList<Timer2>();
            for (int i = 0; i < jsonArray.length(); i++) {
                items.add(i, new Timer2(jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getLong("duration"), null));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Timer2> getItems() {
        return items;
    }

    public void setItems(List<Timer2> items) {
        this.items = items;
    }
}
