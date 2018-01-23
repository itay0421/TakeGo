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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        isRun = true;
        return super.onStartCommand(intent, flags, startId);
    }
}
