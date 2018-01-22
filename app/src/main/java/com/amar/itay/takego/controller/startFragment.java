package com.amar.itay.takego.controller;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;
import com.amar.itay.takego.model.entities.Invitation;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class startFragment extends Fragment implements View.OnClickListener{
    Client client;
    Invitation currentInvitation;
    CarsModel currentCarModel;
    TextView clientName;
    Button start;
    Button stop;
    public startFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_start, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }



    private void findCarModel() {
        TextView CompanyName_TextView = (TextView) getActivity().findViewById(R.id.CompanyName);
        TextView ModelName_TextView = (TextView) getActivity().findViewById(R.id.ModelName);
        TextView EngineCapacity_TextView = (TextView) getActivity().findViewById(R.id.engineCapacity);
        TextView GearBox_TextView = (TextView) getActivity().findViewById(R.id.GearBox);
        TextView SeatsNumber_TextView = (TextView) getActivity().findViewById(R.id.SeatsCar);

        CompanyName_TextView.setText(String.valueOf(currentCarModel.getCompanyName()));
        ModelName_TextView.setText(String.valueOf(currentCarModel.getModelName()));
        EngineCapacity_TextView.setText(String.valueOf(currentCarModel.getEngineCapacity()));
        GearBox_TextView.setText(String.valueOf(currentCarModel.getGearBox()));
        SeatsNumber_TextView.setText(String.valueOf(currentCarModel.getSeatsNumber())+" Seats");

    }

    private void findViews() {
        client = MySQL_DBManager.client;
        String firstName = client.getPrivateName();
        String lastName = client.getFamilyName();
        clientName = (TextView) getActivity().findViewById(R.id.clientName);
        clientName.setText((CharSequence) (firstName + " " + lastName));
        start = (Button) getActivity().findViewById(R.id.button_start);
        stop = (Button) getActivity().findViewById(R.id.button_stop);
        currentInvitation = MySQL_DBManager.invitation;

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
