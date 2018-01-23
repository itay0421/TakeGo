package com.amar.itay.takego.controller;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class startFragment extends Fragment implements View.OnClickListener{
    Client client = MySQL_DBManager.client;
    Invitation currentInvitation = null;
    CarsModel currentCarModel = null;
    TextView clientName;
    Button start;
    Button stop;
    Button closeInvitation;
    ContentValues contentValues = new ContentValues();
    ContentValues contentValues_update = new ContentValues();

    public startFragment() {
        // Required empty public constructor
    }


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



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentCarModel = MySQL_DBManager.currentCarModel;
        findViews();

    }



    private void findCarModelView() {
        TextView CompanyName_TextView;
        TextView ModelName_TextView;
        TextView EngineCapacity_TextView;
        TextView GearBox_TextView;
        TextView SeatsNumber_TextView;
        TextView inUse = getActivity().findViewById(R.id.TextInUse);
        ConstraintLayout carMiniLayout = getActivity().findViewById(R.id.carMiniLayout);
        Button closeInvitation = (Button) getActivity().findViewById(R.id.closeInvitation);
        if (currentInvitation != null) {
            if (currentInvitation.getIsInvitationIsOpen()) {
                LoadCurrentModelCar();
                CompanyName_TextView = (TextView) getActivity().findViewById(R.id.CompanyName);
                ModelName_TextView = (TextView) getActivity().findViewById(R.id.ModelName);
                EngineCapacity_TextView = (TextView) getActivity().findViewById(R.id.engineCapacity);
                GearBox_TextView = (TextView) getActivity().findViewById(R.id.GearBox);
                SeatsNumber_TextView = (TextView) getActivity().findViewById(R.id.SeatsCar);

                CompanyName_TextView.setText(String.valueOf(currentCarModel.getCompanyName()));
                ModelName_TextView.setText(String.valueOf(currentCarModel.getModelName()));
                EngineCapacity_TextView.setText(String.valueOf(currentCarModel.getEngineCapacity()));
                GearBox_TextView.setText(String.valueOf(currentCarModel.getGearBox()));
                SeatsNumber_TextView.setText(String.valueOf(currentCarModel.getSeatsNumber()) + " Seats");
            } else {
//           carMiniLayout.setVisibility(View.GONE);
//            closeInvitation.setVisibility(View.GONE);
//           inUse.setVisibility(View.GONE);
            }
        }
    }

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

    private void findViews() {
            findCarModelView();
        //client = MySQL_DBManager.client;

        String firstName = client.getPrivateName();
        String lastName = client.getFamilyName();
        clientName = (TextView) getActivity().findViewById(R.id.clientName);
        clientName.setText((CharSequence) (firstName + " " + lastName));
        start = (Button) getActivity().findViewById(R.id.button_start);
        stop = (Button) getActivity().findViewById(R.id.button_stop);
        //currentInvitation = MySQL_DBManager.invitation;
        closeInvitation = getActivity().findViewById(R.id.closeInvitation);


        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        closeInvitation.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if( view == start)
            startFragment.this.getActivity().startService(new Intent(startFragment.this.getActivity(),MyIntentService.class));
        else if(view == stop)
            startFragment.this.getActivity().stopService(new Intent(startFragment.this.getActivity(),MyIntentService.class));
        else if(view == closeInvitation)
            closeOpenInvitation();
    }

    private void closeOpenInvitation() {
        contentValues.put(Car_GoConst.InvitationConst.TOTAL_PAYMENT, 0 );
        contentValues.put(Car_GoConst.InvitationConst.FUEL_LITER, "null" );
        contentValues.put(Car_GoConst.InvitationConst.IS_FUEL, "false");
        contentValues.put(Car_GoConst.InvitationConst.START_RENT, "");
        contentValues.put(Car_GoConst.InvitationConst.END_RENT, "");
        contentValues.put(Car_GoConst.InvitationConst.INVITATION_IS_OPEN, "false");
        contentValues.put(Car_GoConst.InvitationConst.CLIENT_ID, "null");
        //Log.d("<<<<<<<***>>>>>>",String.valueOf(client==null));
        Car car = null;
        for(Car carInUse : MySQL_DBManager.allCars)
        {
            if(currentInvitation.getCarNumber()== carInUse.getCarNumber())
                car = carInUse;
        }
        contentValues.put(Car_GoConst.InvitationConst.CAR_NUMBER, car.getCarNumber());
        contentValues_update.put(Car_GoConst.CarConst.CAR_NUMBER,car.getCarNumber() );
        contentValues_update.put(Car_GoConst.CarConst.IN_USE, "false" );








        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                FactoryMethod.getManager().updateCar(contentValues_update);
                FactoryMethod.getManager().updateInvitation(contentValues);
                return null;
            }


        }.execute();
    }
}
