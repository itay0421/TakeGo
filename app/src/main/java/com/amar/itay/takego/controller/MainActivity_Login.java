package com.amar.itay.takego.controller;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amar.itay.takego.R;
import com.amar.itay.takego.controller.MainActivity_Drawer;

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

    }

    @Override
    public void onClick(View v) {
        if ( v == signin ) {
            Intent intent = new Intent(this, MainActivity_Drawer.class);
            startActivity(intent);
        }
    }

}
