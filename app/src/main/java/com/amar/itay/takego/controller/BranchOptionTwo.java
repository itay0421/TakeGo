package com.amar.itay.takego.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BranchOptionTwo extends Fragment {



    ListView listView ;
    List<Branch> myBranchsList;
    List<CarsModel> myCarModelList;
    Branch branchOnOrder = null;
    CarsModel carOnOrder = null;
    boolean nowOnBranch = true;
    Client client = null;

    View view;
    public BranchOptionTwo() {
        // Required empty public constructor
    }




    @SuppressLint("StaticFieldLeak")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_branch_option_two, container, false);
        listView = (ListView) view.findViewById(R.id.listViewBranch);
        client = MySQL_DBManager.client;

        myBranchsList = MySQL_DBManager.branchList;
        ArrayAdapter<Branch> adapter = new ArrayAdapter<Branch>(getContext(), R.layout.branch_mini_layout, myBranchsList) {

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                if (convertView == null) {
                    convertView = View.inflate(BranchOptionTwo.this.getActivity(), R.layout.branch_mini_layout, null);
                }

                TextView productId_Location_TextView = (TextView) convertView.findViewById(R.id.locationTextB);
                TextView productId_ParkingSpaces_TextView = (TextView) convertView.findViewById(R.id.ParkingSpacesMini);
                TextView productId_BranchNumber_TextView = (TextView) convertView.findViewById(R.id.BranchNumberMini);

                productId_Location_TextView.setText(String.valueOf(myBranchsList.get(position).getStreet() + " " + myBranchsList.get(position).getBuildingNumber() + ", " + myBranchsList.get(position).getCity()));
                productId_ParkingSpaces_TextView.setText(String.valueOf(myBranchsList.get(position).getParkingSpacesNumber()));
                productId_BranchNumber_TextView.setText(String.valueOf(myBranchsList.get(position).getBranchNumber()));
                return convertView;
            }
        };
        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Branch selectedBranch = (Branch) listView.getItemAtPosition(position);
                    String city = selectedBranch.getCity();
                    String street = selectedBranch.getStreet();
                    intent.setData(Uri.parse("geo:0,0?q= " + street + " " + city + ", Israel"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "can't open location, pls download maps", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    //i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                    i.setData(Uri.parse("http://play.google.com/store/search?q=maps&c=apps"));
                    //i.setData(Uri.parse("market://details?id=com.google.android.apps.maps"));
                    startActivity(i);
                }
                return false;
            }
        });
        //will pick the branch to the invitation
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (nowOnBranch) {
                    branchOnOrder = (Branch) listView.getItemAtPosition(position);
                    final ContentValues contentValuesBranch = Car_GoConst.BranchToContentValues(branchOnOrder);
                    new AsyncTask<Void, View, List<CarsModel>>() {

                        @Override
                        protected List<CarsModel> doInBackground(Void... voids) {
                            myCarModelList = FactoryMethod.getManager().AllAvilableCarsForBranch(contentValuesBranch);
                            return myCarModelList;
                        }

                        @Override
                        protected void onPostExecute(List<CarsModel> cars) {
                            Log.d("<<<<<>>>>>>>",String.valueOf(cars.get(0).getModelCode()));
                            nowOnBranch = false;
                            Log.d("<<<<<<<>>>>>>","jhjhhjgh");
                            if (cars != null) {
                                ArrayAdapter<CarsModel> adapter = new ArrayAdapter<CarsModel>(getContext(), R.layout.car_mini_layout, myCarModelList) {

                                    @NonNull
                                    @Override
                                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                                        if (convertView == null) {
                                            convertView = View.inflate(BranchOptionTwo.this.getActivity(), R.layout.car_mini_layout, null);
                                        }

                                        TextView productId_CompanyName_TextView = (TextView) convertView.findViewById(R.id.CompanyName);
                                        TextView productId_ModelName_TextView = (TextView) convertView.findViewById(R.id.ModelName);
                                        TextView productId_EngineCapacity_TextView = (TextView) convertView.findViewById(R.id.engineCapacity);
                                        TextView productId_GearBox_TextView = (TextView) convertView.findViewById(R.id.GearBox);
                                        TextView productId_SeatsNumber_TextView = (TextView) convertView.findViewById(R.id.SeatsCar);


                                        productId_CompanyName_TextView.setText(String.valueOf(myCarModelList.get(position).getCompanyName()));
                                        productId_ModelName_TextView.setText(String.valueOf(myCarModelList.get(position).getModelName()));
                                        productId_EngineCapacity_TextView.setText(String.valueOf(myCarModelList.get(position).getEngineCapacity()));
                                        productId_GearBox_TextView.setText(String.valueOf(myCarModelList.get(position).getGearBox()));
                                        productId_SeatsNumber_TextView.setText(String.valueOf(myCarModelList.get(position).getSeatsNumber())+" Seats");

                                        return convertView;
                                    }
                                };
                                listView.setAdapter(adapter);
                            }
                            else {
                                nowOnBranch = true;
                                Toast.makeText(getActivity(), "this branch don`t own cars yet", Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();

                }
            else
            {
                carOnOrder = (CarsModel) listView.getItemAtPosition(position);
               find();
            }
        }});


        return view;
    }
    public void find()
    {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BranchOptionTwo.this.getActivity());
//        alertDialogBuilder.setTitle("Confirm Invitation");
//        String massege = "Hello "+"<b>"+client.getPrivateName()+client.getFamilyName()+"</b>,<br/><br/>"+
//                "We want to make sure that the order is right for you,<br/>" +
//                "please confirm your oreder.<br/><br/>"+
//                "Car number: "+"<b>" + carOnOrder.getCarNumber()+ "</b><br/>" +
//                //"Model: "+"<b>" + selectedcarsModel.getCompanyName() +" "+ selectedcarsModel.getModelName()+ "</b><br/>" +
//                "Model: "+"<b>" + carOnOrder.getCarNumber() +" "+ carOnOrder.getCarNumber()+ "</b><br/>" +
//                "Branch: "+"<b>" + branchOnOrder.getCity() +", "+ branchOnOrder.getStreet() +" "+
//                branchOnOrder.getBuildingNumber()+ "</b><br/><br/>" +
//                "you pay with credit card " + client.getCreditCard() + "<br/>" +
//                "we start your renting from today." + "<br/><br/>"
//                + "Are you approves the order? <br/>";




//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            alertDialogBuilder.setMessage(Html.fromHtml(massege, Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            alertDialogBuilder.setMessage(Html.fromHtml(massege));
//        }
//        alertDialogBuilder.setPositiveButton("Ok",onClickListener);
//        alertDialogBuilder.setNegativeButton("Cancel ",onClickListener);
//
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show( );

    }
    AlertDialog.OnClickListener onClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
