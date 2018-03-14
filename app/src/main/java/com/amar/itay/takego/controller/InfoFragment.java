package com.amar.itay.takego.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.entities.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * this class show the information about the rent car company.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{

    //definition for the instance views we will get.
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
    TextView moreInfo;
    Activity activityContext;

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
        ((MainActivity_Drawer) getActivity()).setActionBarTitle("About us");
        activityContext = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    /**
     * this function call the find the views function.
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }

    /**
     * find the views.
     */
    private void findViews() {
        phoneNumberRelative = activityContext.findViewById(R.id.phoneNumberRelative);
        emailRelative = activityContext.findViewById(R.id.emailRelative);
        webRelative = activityContext.findViewById(R.id.webRelative);
        phoneNumber = (TextView) activityContext.findViewById(R.id.phoneNumberText);
        emailTo = (TextView) activityContext.findViewById(R.id.EmailText);
        webText = (TextView) activityContext.findViewById(R.id.webText);
        moreInfo = (TextView) activityContext.findViewById(R.id.moreInfoTextView);

        phoneNumberRelative.setOnClickListener(this);
        emailRelative.setOnClickListener(this);
        webRelative.setOnClickListener(this);
        moreInfo.setOnClickListener(this);
    }

    /**
     * default constructor.
     */
    public InfoFragment() {
    }

    /**
     * check if the click is one of the button that register to the click event.
     * for everyone of them he makes different things :
     * 1) for the phoneNumber button click he opens the calling app.
     * 2) for the email button click he opens the email app.
     * 3) for the web button click he opens the website of the company.
     * 4) for the moreInfo text click he opens the moreInfo activity to display more information.
     * 4) for the location button click he opens the maps app.
     * @param v represent the view of the event that have been occurred.
     */
    @Override
    public void onClick(View v) {
        Intent intent =null;
        if (v == phoneNumberRelative) {

            //Implicit intent
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
            startActivity(intent);

        } else if (v == emailRelative) {
            //Implicit intent
            intent = new Intent(Intent.ACTION_SEND);
            //intent.setDataAndType(Uri.parse("mailto:"),"message/rfc822");doesnt work
            intent.setData(Uri.parse("mailto:"));
            intent.setType("message/rfc822");
            String [] to = {emailTo.getText().toString()};
            intent.putExtra(Intent.EXTRA_EMAIL,to);
            Intent chooser = Intent.createChooser(intent,"Send Email");
            startActivity(chooser);

            //
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","message/rfc822", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
            //
        } else if (v == webRelative) {
            intent = new Intent(activityContext, RentCarWebSite.class);
            intent.putExtra("webUrl",webText.getText().toString());
            startActivity(intent);
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webText.getText().toString()));
//            startActivity(intent);
        }else if( v == moreInfo) {
             Fragment fragment = new DetailsInfoFragment();
            if(fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragLinearLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
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
                Toast.makeText(getActivity(),"app not installed",Toast.LENGTH_LONG).show();
            }
        }
    }
}
