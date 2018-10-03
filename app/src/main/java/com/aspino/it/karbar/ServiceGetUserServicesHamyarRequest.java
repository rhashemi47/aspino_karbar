package com.aspino.it.karbar;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by hashemi on 02/18/2018.
 */

public class ServiceGetUserServicesHamyarRequest extends Service {
    Handler mHandler;
    boolean continue_or_stop = true;
    boolean createthread=true;
    private DatabaseHelper dbh;
    private SQLiteDatabase db;
    private String karbarCode;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        continue_or_stop=true;
        if(createthread) {
            mHandler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (continue_or_stop) {
                        try {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (PublicVariable.thread_RequestHamyar) {
                                        dbh = new DatabaseHelper(getApplicationContext());
                                        try {

                                            dbh.createDataBase();

                                        } catch (IOException ioe) {

                                            throw new Error("Unable to create database");

                                        }

                                        try {

                                            dbh.openDataBase();

                                        } catch (SQLException sqle) {

                                            throw sqle;
                                        }
                                        db = dbh.getReadableDatabase();
                                        Cursor coursors = db.rawQuery("SELECT * FROM login", null);
                                        for (int i = 0; i < coursors.getCount(); i++) {
                                            coursors.moveToNext();
                                            karbarCode = coursors.getString(coursors.getColumnIndex("karbarCode"));
                                        }
                                        db.close();
                                        SyncGetUserServicesHamyarRequest syncGetUserServicesHamyarRequest = new SyncGetUserServicesHamyarRequest(getApplicationContext(), karbarCode, "0");
                                        syncGetUserServicesHamyarRequest.AsyncExecute();
                                    }
                                }
                            });
                            Thread.sleep(6000); // every 60 seconds
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
            }).start();
            createthread=false;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        continue_or_stop=false;
    }
}
