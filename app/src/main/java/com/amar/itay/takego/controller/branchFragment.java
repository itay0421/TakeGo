package com.amar.itay.takego.controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.entities.Branch;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class branchFragment extends Fragment  {


    ListView listView ;
    List<Branch> myBranchsList;
    View view;

    public branchFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_branch, container, false);
        listView = (ListView)view.findViewById(R.id.branch_listview);

        new AsyncTask<Void, View, List<Branch>>() {

            @Override
            protected List<Branch> doInBackground(Void... voids) {
                myBranchsList = FactoryMethod.getManager().AllBranch();
                return myBranchsList;
            }

            @Override
            protected void onPostExecute(List<Branch> branches) {
                ArrayAdapter<Branch> adapter = new ArrayAdapter<Branch>(getContext(), R.layout.branch_list, myBranchsList)
                {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)    {
                            convertView = View.inflate(branchFragment.this.getActivity(), R.layout.branch_list,null);
                        }

                        TextView productId_City_TextView = (TextView) convertView.findViewById(R.id.street);
                        TextView productId_Street_TextView = (TextView) convertView.findViewById(R.id.city);
                        // TextView productId_BuildingNumber_TextView = (TextView) convertView.findViewById(R.id._bulidingNumber);
                        //TextView productId_ParkingSpaces_TextView = (TextView) convertView.findViewById(R.id._ParkingSpaces);
                        //TextView productId_BranchNumber_TextView = (TextView) convertView.findViewById(R.id._BranchNumber);

                        productId_City_TextView.setText(myBranchsList.get(position).getCity().toString());
                        productId_Street_TextView.setText((myBranchsList.get(position).getStreet()).toString()+", ");
                        //productId_BuildingNumber_TextView.setText(((Integer) myBranchsList.get(position).getBuildingNumber()).toString());
                        //productId_ParkingSpaces_TextView.setText(((Integer) myBranchsList.get(position).getParkingSpacesNumber()).toString());
                        //productId_BranchNumber_TextView.setText(((Integer) myBranchsList.get(position).getBranchNumber()).toString());
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
