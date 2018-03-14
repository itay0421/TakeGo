package com.amar.itay.takego.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Invitation;

import java.util.List;

/**
 * Created by itay0 on 22/01/2018.
 */

/**
 * listening to events in case any car released by closing invitation.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    /**
     * on receive he will check if the car became free and will make a toast to say that there is a new car.
     * @param context of the activity.
     * @param intent of the calling activity.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        String intentData = intent.getStringExtra("message");

        if (intent.getAction().equals("com.amar.itay.takego.CAR_BECAME_FREE")) {
            Toast.makeText(context, "New cars " + intentData + " available now! Enter the list of vehicles to see what's new", Toast.LENGTH_LONG).show();
            Log.d("BroadcastReceiver: ", "RECIVER WORK! " + intentData);

            new AsyncTask< Void,Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    MySQL_DBManager.carsList = FactoryMethod.getManager().AllCars();
                    return null;
                }
            }.execute();
        }

    }
}
