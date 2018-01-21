package com.amar.itay.takego.controller;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    static int count = 1;
    int id = 0, startId = -1;
    boolean isRun = false;
    final String TAG = "testService";


    public MyIntentService() {
        super("MyIntentService");
        id = count++;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (isRun) {
            try {
                Thread.sleep(1000);
                Log.d("david","call me any time");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, serviceInfo(startId) + " print ...");
        }
    }


    String serviceInfo(int sid) {
        return "service [" + id + "] startId = " + sid;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        id++;
        Log.d(TAG, serviceInfo(startId) + " onCreate ...");

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, serviceInfo(startId) + " onDestroy ...");
        isRun = false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        isRun = true;
        Log.d(TAG, serviceInfo(startId) + " onStartCommand start ...");
        return super.onStartCommand(intent, flags, startId);
    }
}
