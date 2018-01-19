package com.amar.itay.takego.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Fragment;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{


    private ListView listView;
    private List<Car> myList;
    List<ContentValues> contentValuesList = new ArrayList<ContentValues>();
    RelativeLayout phoneNumberRelative;
    RelativeLayout emailRelative;
    RelativeLayout webRelative;
    RelativeLayout locationRelative;

    TextView phoneNumber;
    TextView emailTo;
    TextView city;
    TextView street;
    TextView webText;
    Activity activityContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activityContext = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }
    private void findViews() {
        phoneNumberRelative = activityContext.findViewById(R.id.phoneNumberRelative);
        emailRelative = activityContext.findViewById(R.id.emailRelative);
        webRelative = activityContext.findViewById(R.id.webRelative);
        locationRelative = activityContext.findViewById(R.id.locationRelative);
        phoneNumber = (TextView) activityContext.findViewById(R.id.phoneNumberText);
        emailTo = (TextView) activityContext.findViewById(R.id.EmailText);
        city = (TextView) activityContext.findViewById(R.id.cityText);
        street = (TextView) activityContext.findViewById(R.id.streetText);
        webText = (TextView) activityContext.findViewById(R.id.webText);

        phoneNumberRelative.setOnClickListener(this);
        emailRelative.setOnClickListener(this);
        webRelative.setOnClickListener(this);
        locationRelative.setOnClickListener(this);

    }


    public InfoFragment() {
    }

    @Override
    public void onClick(View v) {
        Intent intent =null;
        if (v == phoneNumberRelative) {
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
            startActivity(intent);
        } else if (v == emailRelative) {
            intent = new Intent(Intent.ACTION_SEND);
            //intent.setDataAndType(Uri.parse("mailto:"),"message/rfc822");doesnt work
            intent.setData(Uri.parse("mailto:"));
            intent.setType("message/rfc822");
            String [] to = {emailTo.getText().toString()};
            intent.putExtra(Intent.EXTRA_EMAIL,to);
            Intent chooser = Intent.createChooser(intent,"Send Email");
            startActivity(chooser);
        } else if (v == webRelative) {
                 intent = new Intent(Intent.ACTION_VIEW,Uri.parse(webText.getText().toString()));
                 startActivity(intent);
        } else if (v == locationRelative) {
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                String City = city.getText().toString();
                String Street = street.getText().toString();
                intent.setData(Uri.parse("geo:0,0?q= " + Street + " " + City));
                startActivity(intent);
            }
            catch (Exception e)
            {
                Toast.makeText(getActivity(),"u dont have app",Toast.LENGTH_LONG).show();
            }
        }
    }
}
