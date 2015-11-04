package com.khasang.poti.poti;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by nadezhda on 13.10.2015.
 */

/*
*
* According to Parcel API reference

Parcel is not a general-purpose serialization mechanism. This class (and the corresponding Parcelable API for placing arbitrary objects into a Parcel) is designed as a high-performance IPC transport. As such, it is not appropriate to place any Parcel data in to persistent storage: changes in the underlying implementation of any of the data in the Parcel can render older data unreadable.
Parcelable is meant for IPC use only and is not designed to be persisted. So you have to convert your object into some sort of persistable data structures like XML, JSON, Serializable.
*
* */
public class Timer2 implements Parcelable {

    private String name;
    private long duration;
    private String color;

    public Timer2() {
    }

    public Timer2(String name, long duration, String color) {
        this.name = name;
        this.duration = duration;
        this.color = color;
    }

    protected Timer2(Parcel in) {
        name = in.readString();
        duration = in.readLong();
        color = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static final Creator<Timer2> CREATOR = new Creator<Timer2>() {
        @Override
        public Timer2 createFromParcel(Parcel in) {
            return new Timer2(in);
        }

        @Override
        public Timer2[] newArray(int size) {
            return new Timer2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeLong(duration);
        parcel.writeString(color);
    }

}
