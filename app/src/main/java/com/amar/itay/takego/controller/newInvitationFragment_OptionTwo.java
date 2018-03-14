package com.amar.itay.takego.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.Html;
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
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 * in this function we did multiply thins.
     * 1) the search dialog (to filter the current element.
     * 2) we did 2 screens:
     *  2.1) the first screen show up to pick the place he want to take the car from there and
     *  2.2) the second screen show all cars and the user chose one that he want after that he shows the AlertDialog.
     *  we used AlertDialog after the user pick the car and the branch we will show to the user
     * dialog text to confirm his invitation.
 */
public class newInvitationFragment_OptionTwo extends Fragment {



    ListView listView ;
    List<Branch> myBranchsList;
    List<CarsModel> myCarModelList = null;
    Branch branchOnOrder = null;
    CarsModel carModelOnOrder = null;
    Car carOnOreder = null;
    boolean nowOnBranch = true;
    Client currentClient = null;
    ContentValues contentValues = new ContentValues();
    ContentValues contentValues_update = new ContentValues();


    View view;

    /**
     * default constructor.
     */
    public newInvitationFragment_OptionTwo() {
        // Required empty public constructor
    }



    /**
     * getting all the views.
     * @param inflater to convert xml to view.
     * @param container of the screen.
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     * @return the view to be display.
     */
    @SuppressLint("StaticFieldLeak")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_invitation_option_two, container, false);
        listView = (ListView) view.findViewById(R.id.listViewBranch);
        currentClient = MySQL_DBManager.client;

        myBranchsList = MySQL_DBManager.branchList;
        ArrayAdapter<Branch> adapter = new ArrayAdapter<Branch>(getContext(), R.layout.branch_mini_layout, myBranchsList) {

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                if (convertView == null) {
                    convertView = View.inflate(newInvitationFragment_OptionTwo.this.getActivity(), R.layout.branch_mini_layout, null);
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
                        protected void onPostExecute(List<CarsModel> carsModelList) {
                            nowOnBranch = false;
                            if (carsModelList != null) {
                                ArrayAdapter<CarsModel> adapter = new ArrayAdapter<CarsModel>(getContext(), R.layout.car_mini_layout, carsModelList) {

                                    @NonNull
                                    @Override
                                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                                        if (convertView == null) {
                                            convertView = View.inflate(newInvitationFragment_OptionTwo.this.getActivity(), R.layout.car_mini_layout, null);
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
                //now selected car (on click) model will send to server
                carModelOnOrder = (CarsModel) listView.getItemAtPosition(position);


                contentValues.put(Car_GoConst.CarConst.MODEL_TYPE, carModelOnOrder.getModelCode());
                contentValues.put(Car_GoConst.CarConst.BRANCH_NUMBER, branchOnOrder.getBranchNumber());

                new AsyncTask<Void, View, Car>() {

                    ProgressDialog asyncDialog = new ProgressDialog(newInvitationFragment_OptionTwo.this.getActivity());

                    @Override
                    protected void onPreExecute() {
                        //set message of the dialog
                        asyncDialog.setMessage("loading");
                        //show dialog
                        asyncDialog.show();
                        super.onPreExecute();
                    }

                    @Override
                    protected Car doInBackground(Void... voids) {
                        carOnOreder = FactoryMethod.getManager().GetCarByModelBranch(contentValues);
                        return carOnOreder;
                    }

                    @Override
                    protected void onPostExecute(Car selected_car) {
                        asyncDialog.dismiss();
                        listView.setAdapter(null);
                        showDialogConfirm(selected_car);

                        super.onPostExecute(selected_car);
                    }


                }.execute();



            }
        }});


        return view;
    }


    private void  showDialogConfirm(Car selected_car){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(newInvitationFragment_OptionTwo.this.getActivity());

        alertDialogBuilder.setTitle("Confirm Invitation");
        String massege = "Hello "+"<b>"+ currentClient.getPrivateName()+ currentClient.getFamilyName()+"</b>,<br/><br/>"+
                "We want to make sure that the order is right for you,<br/>" +
                "please confirm your oreder.<br/><br/>"+
                "Car number: "+"<b>" + MySQL_DBManager.realCarNumber(selected_car.getCarNumber())+ "</b><br/>" +
                "Model: "+"<b>" + carModelOnOrder.getCompanyName() +" "+ carModelOnOrder.getModelName()+ "</b><br/>" +
                "Branch: "+"<b>" + branchOnOrder.getCity() +", "+ branchOnOrder.getStreet() +" "+
                branchOnOrder.getBuildingNumber()+ "</b><br/><br/>" +
                "you pay with credit card " + currentClient.getCreditCard() + "<br/>" +
                "we start your renting from today." + "<br/><br/>"
                + "Are you approves the order? <br/>";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            alertDialogBuilder.setMessage(Html.fromHtml(massege, Html.FROM_HTML_MODE_LEGACY));
        } else {
            alertDialogBuilder.setMessage(Html.fromHtml(massege));
        }
        alertDialogBuilder.setPositiveButton("Ok", onClickListener);
        alertDialogBuilder.setNegativeButton("Cancel ", onClickListener);


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show( );


    }


    AlertDialog.OnClickListener onClickListener = new DialogInterface.OnClickListener() {


        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which)
            {
                case Dialog.BUTTON_NEGATIVE:
                    Toast.makeText(newInvitationFragment_OptionTwo.this.getActivity(), Html.fromHtml("O.K! <br/> you can try choose another car").toString(), Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();


                    //we need to go back here.
                    break;

                case Dialog.BUTTON_POSITIVE:

                    currentClient = MySQL_DBManager.client;

                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date_str = dateFormat.format(date).toString();

                    contentValues.put(Car_GoConst.InvitationConst.TOTAL_PAYMENT, 0 );
                    contentValues.put(Car_GoConst.InvitationConst.FUEL_LITER, "null" );
                    contentValues.put(Car_GoConst.InvitationConst.IS_FUEL, "false");
                    contentValues.put(Car_GoConst.InvitationConst.START_RENT, date_str);
                    contentValues.put(Car_GoConst.InvitationConst.END_RENT, "null");
                    contentValues.put(Car_GoConst.InvitationConst.INVITATION_IS_OPEN, "true");
                    contentValues.put(Car_GoConst.InvitationConst.CLIENT_ID, currentClient.getId());
                    contentValues.put(Car_GoConst.InvitationConst.CAR_NUMBER, carOnOreder.getCarNumber());

                    //update car to be 'in_use'
                    contentValues_update.put(Car_GoConst.CarConst.CAR_NUMBER,carOnOreder.getCarNumber() );
                    contentValues_update.put(Car_GoConst.CarConst.IN_USE, "true" );








                    new AsyncTask<Void, Void, Integer>() {

                        @Override
                        protected Integer doInBackground(Void... params) {
                            FactoryMethod.getManager().updateCar(contentValues_update);
                            int result = FactoryMethod.getManager().addInvitation(contentValues);
                            return result;
                        }


                        @Override
                        protected void onPostExecute(Integer result) {
                            Toast.makeText(newInvitationFragment_OptionTwo.this.getActivity(),
                                    Html.fromHtml("We add your invitation!<br/> Enjoy your new car!<br/> is:").toString() + result, Toast.LENGTH_LONG).show();

                            //we need to go back here.
                            getActivity().onBackPressed();
                            //super.onPostExecute(resoult);
                        }


                    }.execute();

                    break;

            }

        } };
}
