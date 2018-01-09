package com.amar.itay.takego.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amar.itay.takego.R;
import com.amar.itay.takego.controller.MainActivity_Drawer;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;

public class MainActivity_Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private Button signin;


    private void findViews() {
        editText = (EditText)findViewById( R.id.editText );
        signin = (Button)findViewById( R.id.signin );


        signin.setOnClickListener( this );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        findViews();

        //*******just for test.
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MySQL_DBManager.branchList = FactoryMethod.getManager().AllBranch();
                MySQL_DBManager.carsModelList = FactoryMethod.getManager().AllCarsModel();
                return null;
            }

        }.execute();

    }

    @Override
    public void onClick(View v) {
        if ( v == signin ) {
            Intent intent = new Intent(this, MainActivity_Drawer.class);
            startActivity(intent);
        }
    }

}
