package com.amar.itay.takego.controller;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.entities.Branch;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class branchFragment extends Fragment {


    ListView listView ;
    List<Branch> myBranchsList;
    View view;

    public branchFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_branch, container, false);
        listView = (ListView)view.findViewById(R.id.branch_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Branch selectedBranch = (Branch) listView.getItemAtPosition(position);
                    String city = selectedBranch.getCity();
                    String street = selectedBranch.getStreet();
                    intent.setData(Uri.parse("geo:0,0?q= " + street + " " + city + ", Israel"));
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "can't open location, pls download maps", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    //i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                     i.setData(Uri.parse("http://play.google.com/store/search?q=maps&c=apps"));
                    //i.setData(Uri.parse("market://details?id=com.google.android.apps.maps"));
                    startActivity(i);
                }
            }
        });


        new AsyncTask<Void, View, List<Branch>>() {

            @Override
            protected List<Branch> doInBackground(Void... voids) {
                myBranchsList = FactoryMethod.getManager().AllBranch();
                return myBranchsList;
            }

            @Override
            protected void onPostExecute(List<Branch> branches) {
                ArrayAdapter<Branch> adapter = new ArrayAdapter<Branch>(getContext(), R.layout.item_list_branch, myBranchsList)
                {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)    {
                            convertView = View.inflate(branchFragment.this.getActivity(), R.layout.item_list_branch,null);
                        }

                        TextView productId_City_TextView = (TextView) convertView.findViewById(R.id.street);
                        TextView productId_Street_TextView = (TextView) convertView.findViewById(R.id.textview_comp_model);
                        TextView productId_BuildingNumber_TextView = (TextView) convertView.findViewById(R.id.detailsTextView);
                        TextView productId_ParkingSpaces_TextView = (TextView) convertView.findViewById(R.id._ParkingSpaces);
                        TextView productId_BranchNumber_TextView = (TextView) convertView.findViewById(R.id._BranchNumber);

                        productId_City_TextView.setText(myBranchsList.get(position).getCity().toString()+", ");
                        productId_Street_TextView.setText((myBranchsList.get(position).getStreet()).toString()+", ");
                        productId_BuildingNumber_TextView.setText(((Integer) myBranchsList.get(position).getBuildingNumber()).toString());
                        productId_ParkingSpaces_TextView.setText("parking space: " +((Integer) myBranchsList.get(position).getParkingSpacesNumber()).toString());
                        productId_BranchNumber_TextView.setText("id: "+((Integer) myBranchsList.get(position).getBranchNumber()).toString());
                        return convertView;
                    }
                };
                listView.setAdapter(adapter);

            }
        }.execute();
        return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
