package com.amar.itay.takego.controller;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarsFragment extends Fragment {

    private SearchView searchView;
    private ImageView imageView6;
    private TextView textViewCompModel;
    private TextView textViewNumber;
    private TextView textViewSeat;
    private TextView textViewGear;
    private TextView textViewKilimeters;
    private TextView textViewEngin;
    private ListView listView;
    private ImageView imageView7;
    private TextView textViewCity;
    private TextView textViewStreetNumber;
    private TextView textViewParking;
    private TextView textView24;

    List<ContentValues> contentValuesList = new ArrayList<ContentValues>();
    ArrayAdapter<ContentValues> adapter_car_Model;







    public CarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        SearchView searchView = (SearchView)view.findViewById(R.id.searchView2);
        listView = (ListView)view.findViewById( R.id.listView3 );


        new AsyncTask<Void, View, Void>() {
            ProgressDialog asyncDialog = new ProgressDialog(CarsFragment.this.getActivity());

            @Override
            protected void onPreExecute() {
                //set message of the dialog
                asyncDialog.setMessage("loading...please wait");
                //show dialog
                asyncDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                contentValuesList = FactoryMethod.getManager().AllCarModelFree();
                //AllBranchByModel(contentValues);
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {

                adapter_car_Model  = new ArrayAdapter<ContentValues>(getContext(), R.layout.item_list_model, contentValuesList){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        if (convertView == null)    {
                            convertView = View.inflate(CarsFragment.this.getActivity(), R.layout.item_list_car,null);
                        }
                        TextView textview_comp_model = (TextView) convertView.findViewById(R.id.textview_comp_model);
                        TextView textview_number = (TextView) convertView.findViewById(R.id.textview_number);

                        textview_comp_model.setText(contentValuesList.get(position).getAsString(Car_GoConst.CarModelConst.COMPANY_NAME) +
                                " " + contentValuesList.get(position).getAsString(Car_GoConst.CarModelConst.MODEL_NAME));
                        textview_number.setText(MySQL_DBManager.realCarNumber(contentValuesList.get(position).getAsInteger(Car_GoConst.CarConst.CAR_NUMBER)));

                        return convertView;
                    }
                };

                listView.setAdapter(adapter_car_Model);
                asyncDialog.dismiss();
            }
        }.execute();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//not work
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter_car_Model.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                textViewCompModel.setText(contentValuesList.get(position).getAsString(Car_GoConst.CarModelConst.COMPANY_NAME) +
                        " " + contentValuesList.get(position).getAsString(Car_GoConst.CarModelConst.MODEL_NAME));
                textViewNumber.setText(MySQL_DBManager.realCarNumber(contentValuesList.get(position).getAsInteger(Car_GoConst.CarConst.CAR_NUMBER)));
                textViewEngin.setText(contentValuesList.get(position).getAsInteger(Car_GoConst.CarModelConst.ENGINE_CAPACITY).toString() + " L");
                textViewGear.setText(contentValuesList.get(position).getAsString(Car_GoConst.CarModelConst.GEAR_BOX).toString());
                textViewKilimeters.setText(contentValuesList.get(position).getAsInteger(Car_GoConst.CarConst.KILOMETERS )+ " km");
                textViewSeat.setText(contentValuesList.get(position).getAsInteger(Car_GoConst.CarModelConst.SEATS_NUMBER).toString() );

                //branch

                textViewCity.setText(contentValuesList.get(position).getAsString(Car_GoConst.BranchConst.CITY));
                textViewStreetNumber.setText(contentValuesList.get(position).getAsString(Car_GoConst.BranchConst.STREET) +", "+
                        contentValuesList.get(position).getAsInteger(Car_GoConst.BranchConst.BUILDING_NUMBER).toString());
                textViewParking.setText("There is "+contentValuesList.get(position).getAsInteger(Car_GoConst.BranchConst.BUILDING_NUMBER).toString() +
                " Parking spaces are available!");

                imageView6.setVisibility(View.VISIBLE);
                imageView7.setVisibility(View.VISIBLE);
                textView24.setVisibility(View.VISIBLE);


            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        imageView6 = (ImageView)view.findViewById( R.id.imageView6 );
        textViewCompModel = (TextView)view.findViewById( R.id.textView_comp_model );
        textViewNumber = (TextView)view.findViewById( R.id.textView_number );
        textViewSeat = (TextView)view.findViewById( R.id.textView_seat );
        textViewGear = (TextView)view.findViewById( R.id.textView_gear );
        textViewKilimeters = (TextView)view.findViewById( R.id.textView_kilimeters );
        textViewEngin = (TextView)view.findViewById( R.id.textView_engin );
        imageView7 = (ImageView)view.findViewById( R.id.imageView7 );
        textViewCity = (TextView)view.findViewById( R.id.textView_city );
        textViewStreetNumber = (TextView)view.findViewById( R.id.textView_street_number );
        textViewParking = (TextView)view.findViewById( R.id.textView_parking );
        textView24 = (TextView)view.findViewById( R.id.textView24 );






        }

}
