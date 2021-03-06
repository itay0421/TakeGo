package com.amar.itay.takego.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/*
* in this function we did multiply thins.
     * 1) the search dialog (to filter the current element.
     * 2) we did 2 screens:
     *  2.1) the first screen show all cars and the user chose one that he want after that:
     *  2.2) the second screen show up to pick the place he want to take the car from there.
     * we used AlertDialog after the user pick the car and the branch we will show to the user
     * dialog text to confirm his invitation.
 */
public class newInvitationFragment extends Fragment {

    //get list of car model
    protected List<CarsModel> carsModelList;
    protected List<Branch> myCustomBranchsList = null;
    protected Car selected_Car;
    protected int selected_carsModelCode = 0;
    protected CarsModel selectedcarsModel;
    protected int selected_branch = 0;
    protected Branch selectedBranch;

    ContentValues contentValues = new ContentValues();
    ContentValues contentValues_update = new ContentValues();

    String selector = "model";
    Client currentClient;
    ListView listView;
    ArrayAdapter<CarsModel> adapter_carModel;
    TextView textView_instoduction;


    //TODO id notlong


    /**
     * default constructor.
     */
    public newInvitationFragment() {
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
    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity_Drawer) getActivity()).setActionBarTitle("New Invitation");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_invitation, container, false);
        ScrollView scrollView = (ScrollView)view.findViewById(R.id.scrollview);
        final ListView listView = (ListView)view.findViewById(R.id.listView2);
        SearchView searchView = (SearchView)view.findViewById(R.id.searchView);
        currentClient = MySQL_DBManager.client;
        textView_instoduction = (TextView)view. findViewById(R.id.textView_insroduction);
        scrollView.scrollTo(0, scrollView.getTop());
        textView_instoduction.setText(Html.fromHtml( "Hello " +"<b>"+ currentClient.getPrivateName()  + " " + currentClient.getFamilyName()+ "</b>"+ "! <br/>" +
                                                    "Here you can choose the type of car you want.<br/><br/>" +
                                                     "<font size=\"20\">Note that you can filter the results as desired.</font>"));

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

        //srart from top scrolling
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        carsModelList = MySQL_DBManager.carsModelList;
        adapter_carModel  = new ArrayAdapter<CarsModel>(getContext(), R.layout.item_list_model, carsModelList){//wrong! need

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter_carModel.getFilter().filter(s);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

          public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //final int selected_carsModelCode ;

              if (selector == "model"){

                    selectedcarsModel = ((CarsModel)listView.getItemAtPosition(position));
                    selected_carsModelCode = ((CarsModel)listView.getItemAtPosition(position)).getModelCode();
                    contentValues.put(Car_GoConst.CarConst.MODEL_TYPE,selected_carsModelCode );

                    MySQL_DBManager.currentCarModel =  ((CarsModel)listView.getItemAtPosition(position));
                    Log.d("***************",((CarsModel)listView.getItemAtPosition(position)).getCompanyName() );

                    //clear listview and load branch list
                    //listView.setAdapter(null);

                    //used to show all the cars and to chose one of them
                    new AsyncTask<Void, View, List<Branch>>() {
                        ProgressDialog asyncDialog = new ProgressDialog(newInvitationFragment.this.getActivity());

                        @Override
                        protected void onPreExecute() {
                            textView_instoduction.setText(Html.fromHtml("Your choice is great!<br/>  Now select your nearest branch."));

                            //set message of the dialog
                            asyncDialog.setMessage("loading");
                            //show dialog
                            asyncDialog.show();
                            super.onPreExecute();
                        }

                        @Override
                        protected List<Branch> doInBackground(Void... voids) {
                            myCustomBranchsList = FactoryMethod.getManager().AllBranchByModel(contentValues);
                            //AllBranchByModel(contentValues);
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
                                    TextView productId_Street_TextView = (TextView) convertView.findViewById(R.id.textview_comp_model);
                                    TextView productId_BuildingNumber_TextView = (TextView) convertView.findViewById(R.id.detailsTextView);
                                    TextView productId_ParkingSpaces_TextView = (TextView) convertView.findViewById(R.id._ParkingSpaces);
                                    TextView productId_BranchNumber_TextView = (TextView) convertView.findViewById(R.id._BranchNumber);

                                    productId_City_TextView.setText(myCustomBranchsList.get(position).getCity().toString()+", ");
                                    productId_Street_TextView.setText((myCustomBranchsList.get(position).getStreet()).toString()+", ");
                                    productId_BuildingNumber_TextView.setText(((Integer) myCustomBranchsList.get(position).getBuildingNumber()).toString());
                                    productId_ParkingSpaces_TextView.setText("parking space: " + ((Integer) myCustomBranchsList.get(position).getParkingSpacesNumber()).toString());
                                    productId_BranchNumber_TextView.setText("id: "+((Integer) myCustomBranchsList.get(position).getBranchNumber()).toString());
                                    return convertView;
                                }
                            };
                            if (branches.size() == 0) {
                                textView_instoduction.setText(Html.fromHtml("We're really sorry!<br/>" +
                                        "The car you selected is not available at any branch.<br/>" +
                                        "Try selecting another car."));

                            }
                            listView.setAdapter(adapter_Branch);
                            asyncDialog.dismiss();
                        }
                    }.execute();
                  selector = "branch" ;

                }
                else if(selector == "branch"){ //after user select branch (by model)

                    selectedBranch = ((Branch)listView.getItemAtPosition(position));
                    selected_branch = ((Branch)listView.getItemAtPosition(position)).getBranchNumber();
                    contentValues.put(Car_GoConst.CarConst.BRANCH_NUMBER, selected_branch );

                    //clear listview and load branch list
                   //used to show the branch-s and pick one of them
                    new AsyncTask<Void, View, Car>() {
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
                        protected Car doInBackground(Void... voids) {

                            selected_Car = FactoryMethod.getManager().GetCarByModelBranch(contentValues);
                            return selected_Car;
                            //because it non UI-tread, we cant change here global object of main UI
                        }

                        @Override
                        protected void onPostExecute(Car car) {
                            asyncDialog.dismiss();
                            listView.setAdapter(null);
                            selected_Car = car;
                            //here we can. onPostExecute it run on UI main
                            //Toast.makeText(newInvitationFragment.this.getActivity(), "the car"+ selected_Car, Toast.LENGTH_LONG).show();

                            //showConfirmInvitation();
                            //dialog for add invitation
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(newInvitationFragment.this.getActivity());
                            alertDialogBuilder.setTitle("Confirm Invitation");
                            String massege = "Hello "+"<b>"+currentClient.getPrivateName()+currentClient.getFamilyName()+"</b>,<br/><br/>"+
                                    "We want to make sure that the order is right for you,<br/>" +
                                    "please confirm your oreder.<br/><br/>"+
                                    "Car number: "+"<b>" + MySQL_DBManager.realCarNumber(selected_Car.getCarNumber())+ "</b><br/>" +
                                    "Model: "+"<b>" + selectedcarsModel.getCompanyName() +" "+ selectedcarsModel.getModelName()+ "</b><br/>" +
                                    "Branch: "+"<b>" + selectedBranch.getCity() +", "+ selectedBranch.getStreet() +" "+
                                                            selectedBranch.getBuildingNumber()+ "</b><br/><br/>" +
                                    "you pay with credit card " + currentClient.getCreditCard() + "<br/>" +
                                    "we start your renting from today." + "<br/><br/>"
                                    + "Are you approves the order? <br/>";




                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                alertDialogBuilder.setMessage(Html.fromHtml(massege, Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                alertDialogBuilder.setMessage(Html.fromHtml(massege));
                            }
                            alertDialogBuilder.setPositiveButton("Ok",onClickListener);
                            alertDialogBuilder.setNegativeButton("Cancel ",onClickListener);


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show( );


                        }

                    }.execute();

              }

            }
        });
        return view;
    }




    AlertDialog.OnClickListener onClickListener = new DialogInterface.OnClickListener() {


        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which)
            {
                case Dialog.BUTTON_NEGATIVE:
                    Toast.makeText(newInvitationFragment.this.getActivity(), Html.fromHtml("O.K! <br/> you can try choose another car").toString(), Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();


                    //we need to go back here.
                    break;

                case Dialog.BUTTON_POSITIVE:
                    currentClient = MySQL_DBManager.client;
                    Log.d("<<<<<<<@@@@@@@>>>>>>",String.valueOf(currentClient.getId()));
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
                    //Log.d("<<<<<<<***>>>>>>",String.valueOf(client==null));
                    contentValues.put(Car_GoConst.InvitationConst.CAR_NUMBER, selected_Car.getCarNumber());
                    contentValues_update.put(Car_GoConst.CarConst.CAR_NUMBER,selected_Car.getCarNumber() );
                    contentValues_update.put(Car_GoConst.CarConst.IN_USE, "true" );

                    //MySQL_DBManager.currentInvitation = Car_GoConst.ContentValuesToInvitation(contentValues);






                    //used to add the new invitation.
                    new AsyncTask<Void, Void, Integer>() {

                        @Override
                        protected Integer doInBackground(Void... params) {
                            FactoryMethod.getManager().updateCar(contentValues_update);
                            int result = FactoryMethod.getManager().addInvitation(contentValues);
                            return result;
                        }


                        @Override
                        protected void onPostExecute(Integer result) {
                            Toast.makeText(newInvitationFragment.this.getActivity(),
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
