package com.amar.itay.takego.controller;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.entities.Invitation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * every interval time the service check if  a car has been released.
 */
public class MyIntentService extends IntentService {

    static int count = 1;
    int id = 0, startId = -1;
    boolean isRun = false;
    final String TAG = "testService";

    /**
     * constructor
     */
    public MyIntentService() {
        super("MyIntentService");
        id = count++;
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * @param intent that was used to bind to this service.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        while (isRun) {
            try {
                Thread.sleep(10000);


//                Date tenSecAgo = new Date(System.currentTimeMillis() - 10000);
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String date_str = dateFormat.format(tenSecAgo).toString();


                List<Invitation> invitationList_at_10_sec = new ArrayList<Invitation>(FactoryMethod.getManager().checkChangeAtLast10Sec());

                if(invitationList_at_10_sec.size() > 0) {

                    int size = invitationList_at_10_sec.size();
                    Log.d("list resuls size: ", String.valueOf(size));


                    Intent intent1 = new Intent();
                    intent1.putExtra("message", String.valueOf(size));
                    intent1.setAction("com.amar.itay.takego.CAR_BECAME_FREE");
                    sendBroadcast(intent1);

                    invitationList_at_10_sec.clear();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        id++;

    }

    @Override
    public void onDestroy() {
        isRun = false;
        super.onDestroy();
    }

    /**
     *
     * The system invokes this method by calling startService() when another component (such as an activity)
     * requests that the service be started.
     * When this method executes, the service is started and can run in the background indefinitely.
     * @param intent that was used to bind to this service.
     * @param flags (not used)
     * @param startId to insert the start id.
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        isRun = true;
        return super.onStartCommand(intent, flags, startId);
    }
}
