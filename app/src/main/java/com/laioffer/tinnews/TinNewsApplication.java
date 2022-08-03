package com.laioffer.tinnews;

import android.app.Application;

import androidx.room.Room;

import com.laioffer.tinnews.database.TinNewsDatabase;

// App's application service the all app
public class TinNewsApplication extends Application {
    private static TinNewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();


        // create database
        database = Room.databaseBuilder(this, TinNewsDatabase.class, "tinnews_db").build();
    }

    // can not put in MainActivity
    // 因为这样会产生memory leak, 因为这样MainActivity 就不会被回收（database hold住MainActivity不被回收）， 会产生多个MainActivity不会被回收
    // static 跟着class 走，就一直存在， 在这里就一致跟着TinNewsApplication
    public static TinNewsDatabase getDatabase() {
        return database;
    }
}

