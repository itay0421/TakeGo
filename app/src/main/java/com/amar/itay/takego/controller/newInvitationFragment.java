package com.amar.itay.takego.controller;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class newInvitationFragment extends Fragment {

    //get list of car model
    List<CarsModel> carsModelList;
    List<Branch> myCustomBranchsList = null;
    Car selected_Car;
    int selected_carsModelCode = 0;
    int selected_branch = 0;
    ContentValues contentValues = new ContentValues();



    public newInvitationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_invitation, container, false);

        final ListView listView = (ListView)view.findViewById(R.id.listView2);
        final ListView listView2 = (ListView)view.findViewById(R.id.listView2);

        //solution for scrolling ListView inside a ScrollView
        listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        carsModelList = MySQL_DBManager.carsModelList;



        ArrayAdapter<CarsModel> adapter_carModel  = new ArrayAdapter<CarsModel>(getContext(), R.layout.item_list_model, carsModelList){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                if (convertView == null)    {
                    convertView = View.inflate(newInvitationFragment.this.getActivity(), R.layout.item_list_model,null);
                }
                TextView textView_modelNumber = (TextView) convertView.findViewById(R.id.textView_modelNumber);
                TextView textView_conpany = (TextView) convertView.findViewById(R.id.textView_conpany);
                TextView textView_model = (TextView) convertView.findViewById(R.id.textView_model);
                TextView textView_seats = (TextView) convertView.findViewById(R.id.textView_seats);
                TextView textView_gearbox = (TextView) convertView.findViewById(R.id.textView_gearbox);


                textView_modelNumber.setText("code: " + ((Integer)carsModelList.get(position).getModelCode()).toString());
                textView_conpany.setText((carsModelList.get(position).getCompanyName()).toString());
                textView_model.setText((carsModelList.get(position).getModelName()).toString());
                textView_seats.setText("Seats: " + ((Integer)carsModelList.get(position).getSeatsNumber()).toString());
                textView_gearbox.setText(", " + (carsModelList.get(position).getGearBox().toString()));

                return convertView;
            }
        };

        listView.setAdapter(adapter_carModel);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){



            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //final int selected_carsModelCode ;


                String selector = "model";



                if (selector == "model"){

                    selected_carsModelCode = ((CarsModel)listView.getItemAtPosition(position)).getModelCode();
                    contentValues.put(Car_GoConst.CarConst.MODEL_TYPE,selected_carsModelCode );

                    //clear listview and load branch list
                    listView.setAdapter(null);
                    new AsyncTask<Void, View, List<Branch>>() {
                        ProgressDialog asyncDialog = new ProgressDialog(newInvitationFragment.this.getActivity());

                        @Override
                        protected void onPreExecute() {
                            //set message of the dialog
                            asyncDialog.setMessage("loading");
                            //show dialog
                            asyncDialog.show();
                            super.onPreExecute();
                        }

                        @Override
                        protected List<Branch> doInBackground(Void... voids) {
                            myCustomBranchsList = FactoryMethod.getManager().AllBranchByModel(contentValues);
                            return myCustomBranchsList;
                        }

                        @Override
                        protected void onPostExecute(List<Branch> branches) {
                            ArrayAdapter<Branch> adapter_Branch = new ArrayAdapter<Branch>(getContext(), R.layout.item_list_branch, myCustomBranchsList)
                            {

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {

                                    if (convertView == null)    {
                                        convertView = View.inflate(newInvitationFragment.this.getActivity(), R.layout.item_list_branch,null);
                                    }

                                    TextView productId_City_TextView = (TextView) convertView.findViewById(R.id.street);
                                    TextView productId_Street_TextView = (TextView) convertView.findViewById(R.id.city);
                                    // TextView productId_BuildingNumber_TextView = (TextView) convertView.findViewById(R.id._bulidingNumber);
                                    //TextView productId_ParkingSpaces_TextView = (TextView) convertView.findViewById(R.id._ParkingSpaces);
                                    //TextView productId_BranchNumber_TextView = (TextView) convertView.findViewById(R.id._BranchNumber);

                                    productId_City_TextView.setText(myCustomBranchsList.get(position).getCity().toString());
                                    productId_Street_TextView.setText((myCustomBranchsList.get(position).getStreet()).toString()+", ");
                                    //productId_BuildingNumber_TextView.setText(((Integer) myBranchsList.get(position).getBuildingNumber()).toString());
                                    //productId_ParkingSpaces_TextView.setText(((Integer) myBranchsList.get(position).getParkingSpacesNumber()).toString());
                                    //productId_BranchNumber_TextView.setText(((Integer) myBranchsList.get(position).getBranchNumber()).toString());
                                    return convertView;
                                }
                            };
                            asyncDialog.dismiss();
                            listView2.setAdapter(adapter_Branch);

                        }
                    }.execute();
                selector = "branch" ;



                }
                else if(selector == "branch"){ //after user select branch

                    selected_branch = ((Branch)listView.getItemAtPosition(position)).getBranchNumber();
                    contentValues.put(Car_GoConst.CarConst.BRANCH_NUMBER, selected_branch );

                    //clear listview and load branch list

                    new AsyncTask<Void, View, Void>() {
                        ProgressDialog asyncDialog = new ProgressDialog(newInvitationFragment.this.getActivity());

                        @Override
                        protected void onPreExecute() {
                            //set message of the dialog
                            asyncDialog.setMessage("loading");
                            //show dialog
                            asyncDialog.show();
                            super.onPreExecute();
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            asyncDialog.dismiss();                        }

                        @Override
                        protected Void doInBackground(Void... voids) {

                            selected_Car = FactoryMethod.getManager().GetCarByModelBranch(contentValues);
                            return null;
                        }


                    }.execute();


                }





            }
        });
        return view;
    }



}
