package com.amar.itay.takego.controller;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;
import com.amar.itay.takego.model.entities.Invitation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;


public class startFragment extends Fragment implements View.OnClickListener{
    Client client = MySQL_DBManager.client;
    Invitation currentInvitation = null;
    CarsModel currentCarModel = null;
    TextView clientName;
    TextView TextViewKilometers;
    EditText Kilometer;
    ImageView imageKilometers;
    Button start;
    Button stop;
    TextView inUse;
    TextView startText;
    ConstraintLayout carMiniLayout;
    Button closeInvitation;
    ContentValues contentValues = new ContentValues();
    ContentValues contentValues_update = new ContentValues();

    /**
     * default constructor.
     */
    public startFragment() {
        // Required empty public constructor
    }

    /**
     * @param inflater to convert xml to view.
     * @param container of the screen.
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     * @return the view to be display.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);
            new AsyncTask<Void, Void, Invitation>() {
                @Override
                protected void onPostExecute(Invitation invitation) {
                    findCarModelView();
                 if(invitation == null)
                    Toast.makeText(startFragment.this.getActivity(),"no open invitation for client: "+client.getId(), Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Invitation doInBackground(Void... voids) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Car_GoConst.ClientConst.ID, client.getId());
                    currentInvitation = FactoryMethod.getManager().getAllOpenInvitation(contentValues);

                    //currentInvitation = FactoryMethod.getManager().getAllOpenInvitation(Car_GoConst.ClientToContentValues(MySQL_DBManager.client));

                    return currentInvitation;
                }

            }.execute();


        return view;
    }


     /**
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentCarModel = MySQL_DBManager.currentCarModel;
        findViews();

    }


    /**
     * findViews of car models.
     */
    private void findCarModelView() {
        TextView CompanyName_TextView;
        TextView ModelName_TextView;
        TextView EngineCapacity_TextView;
        TextView GearBox_TextView;
        TextView SeatsNumber_TextView;
        TextView textView_carNumber;

        inUse = getActivity().findViewById(R.id.TextInUse);
        carMiniLayout = getActivity().findViewById(R.id.carMiniLayout);
        closeInvitation = (Button) getActivity().findViewById(R.id.closeInvitation);
        if (currentInvitation != null) {
            if (currentInvitation.getIsInvitationIsOpen()) {
                /////
                carMiniLayout.setVisibility(View.VISIBLE);
                closeInvitation.setVisibility(View.VISIBLE);
                inUse.setVisibility(View.VISIBLE);
                TextViewKilometers.setVisibility(View.VISIBLE);
                imageKilometers.setVisibility(View.VISIBLE);
                Kilometer.setVisibility(View.VISIBLE);
                startText.setVisibility(View.GONE);

                /////
                LoadCurrentModelCar();
                CompanyName_TextView = (TextView) getActivity().findViewById(R.id.CompanyName);
                ModelName_TextView = (TextView) getActivity().findViewById(R.id.ModelName);
                EngineCapacity_TextView = (TextView) getActivity().findViewById(R.id.engineCapacity);
                GearBox_TextView = (TextView) getActivity().findViewById(R.id.GearBox);
                SeatsNumber_TextView = (TextView) getActivity().findViewById(R.id.SeatsCar);
                textView_carNumber = (TextView) getActivity().findViewById(R.id.textView_carNumber);

                CompanyName_TextView.setText(String.valueOf(currentCarModel.getCompanyName()));
                ModelName_TextView.setText(String.valueOf(currentCarModel.getModelName()));
                EngineCapacity_TextView.setText(String.valueOf(currentCarModel.getEngineCapacity()));
                GearBox_TextView.setText(String.valueOf(currentCarModel.getGearBox()));
                SeatsNumber_TextView.setText(String.valueOf(currentCarModel.getSeatsNumber()) + " Seats");
                textView_carNumber.setText(MySQL_DBManager.realCarNumber(currentInvitation.getCarNumber()));
            }
        } else {
            carMiniLayout.setVisibility(View.GONE);
            closeInvitation.setVisibility(View.GONE);
            inUse.setVisibility(View.GONE);
            TextViewKilometers.setVisibility(View.GONE);
            imageKilometers.setVisibility(View.GONE);
            Kilometer.setVisibility(View.GONE);
            startText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * load the current model car.
     */
    private void LoadCurrentModelCar() {
        List<Car> carList = new ArrayList<>();

        for(Car car:MySQL_DBManager.allCars)
        {
            if(car.getCarNumber() == currentInvitation.getCarNumber())
                carList.add(car);
        }
        for(CarsModel carsModel: MySQL_DBManager.carsModelList)
        {
            for(Car car :carList) {
                if (carsModel.getModelCode() == car.getModelType()) {
                    MySQL_DBManager.currentCarModel = carsModel;
                    currentCarModel = carsModel;
                }
            }
        }

    }

    /**
     * find views.
     */
    private void findViews() {
        Kilometer = (EditText) getActivity().findViewById(R.id.kilometersText);
        TextViewKilometers = (TextView) getActivity().findViewById(R.id.TextViewKilometers);
        imageKilometers = (ImageView) getActivity().findViewById(R.id.imageKilometers);
        startText =(TextView) getActivity().findViewById(R.id.startText);
        findCarModelView();
        //client = MySQL_DBManager.client;

        String firstName = client.getPrivateName();
        String lastName = client.getFamilyName();
        clientName = (TextView) getActivity().findViewById(R.id.clientName);
        clientName.setText((CharSequence) (firstName + " " + lastName));
        start = (Button) getActivity().findViewById(R.id.button_start);
        stop = (Button) getActivity().findViewById(R.id.button_stop);
        //currentInvitation = MySQL_DBManager.invitation;
       // closeInvitation = getActivity().findViewById(R.id.closeInvitation);


        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        closeInvitation.setOnClickListener(this);
    }

    /**
     * @param view represent the view of the event that have been occurred.
     */
    @Override
    public void onClick(View view) {
        if( view == start)
            startFragment.this.getActivity().startService(new Intent(startFragment.this.getActivity(),MyIntentService.class));
        else if(view == stop)
            startFragment.this.getActivity().stopService(new Intent(startFragment.this.getActivity(),MyIntentService.class));
        else if(view == closeInvitation)
            closeOpenInvitation();
    }

    /**
     * closing open invitation and insert the kilometers to calculate the payment.
     */
    private void closeOpenInvitation() {

        if (Kilometer.getText().length() == 0) {
            TextViewKilometers.setTextColor(Color.RED);
            TextViewKilometers.setText("You must enter car kilometers to continue");
        } else {
            contentValues.put(Car_GoConst.InvitationConst.INVITATION_ID, currentInvitation.getInvitationId());

            carMiniLayout.setVisibility(View.GONE);
            closeInvitation.setVisibility(View.GONE);
            inUse.setVisibility(View.GONE);
            TextViewKilometers.setVisibility(View.GONE);
            imageKilometers.setVisibility(View.GONE);
            Kilometer.setVisibility(View.GONE);
            startText.setVisibility(View.VISIBLE);

            //total payment
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10);
            double total_payment = randomNum * 100.90;
            contentValues.put(Car_GoConst.InvitationConst.TOTAL_PAYMENT, total_payment);

            //about gas
            randomNum = ThreadLocalRandom.current().nextInt(0, 10);
            if (randomNum == 0) {
                contentValues.put(Car_GoConst.InvitationConst.IS_FUEL, "false");
            } else {
                int fuel_liters = randomNum * 30;
                contentValues.put(Car_GoConst.InvitationConst.FUEL_LITER, fuel_liters);
                contentValues.put(Car_GoConst.InvitationConst.IS_FUEL, "true");
            }

            //date of close order
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
            String date_str = "'" + dateFormat.format(date).toString() + "'";
            Log.d("*******date_str", date_str);
            contentValues.put(Car_GoConst.InvitationConst.END_RENT, date_str);

            contentValues.put(Car_GoConst.InvitationConst.INVITATION_IS_OPEN, "false");

            //Log.d("<<<<<<<***>>>>>>",String.valueOf(client==null));
//        Car car = null;
//        for(Car carInUse : MySQL_DBManager.allCars)
//        {
//            if(currentInvitation.getCarNumber()== carInUse.getCarNumber())
//                car = carInUse;
//        }
//        contentValues.put(Car_GoConst.InvitationConst.CAR_NUMBER, car.getCarNumber());

            //update car to be not in use
            contentValues_update.put(Car_GoConst.CarConst.CAR_NUMBER, currentInvitation.getCarNumber());
            contentValues_update.put(Car_GoConst.CarConst.IN_USE, "false");


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPostExecute(Void aVoid) {
                    Toast.makeText(getActivity(), "Invitaion number" + currentInvitation.getInvitationId() + "is closed", Toast.LENGTH_LONG).show();
                    currentInvitation = null;
                }

                @Override
                protected Void doInBackground(Void... params) {

                    FactoryMethod.getManager().updateCar(contentValues_update);
                    FactoryMethod.getManager().updateInvitation(contentValues);
                    return null;
                }


            }.execute();
        }
    }
}