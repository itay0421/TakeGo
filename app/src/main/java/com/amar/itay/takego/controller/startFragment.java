package com.amar.itay.takego.controller;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
    Client client = null;
    Invitation currentInvitation = null;
    CarsModel currentCarModel = null;
    TextView clientName;
    Button start;
    Button stop;
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
                    contentValues.put("client_id", client.getId());
                    currentInvitation = FactoryMethod.getManager().getAllOpenInvitation(contentValues);

                    //currentInvitation = FactoryMethod.getManager().getAllOpenInvitation(Car_GoConst.ClientToContentValues(MySQL_DBManager.client));

                    return currentInvitation;
                }
            }.execute();

            client = MySQL_DBManager.client;
            // Inflate the layout for this fragment
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
            if (currentCarModel == null) {
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

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if( view == start)
            startFragment.this.getActivity().startService(new Intent(startFragment.this.getActivity(),MyIntentService.class));
        else if(view == stop)
            startFragment.this.getActivity().stopService(new Intent(startFragment.this.getActivity(),MyIntentService.class));
    }
}
